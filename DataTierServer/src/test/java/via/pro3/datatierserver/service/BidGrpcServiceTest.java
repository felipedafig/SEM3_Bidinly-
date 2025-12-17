package via.pro3.datatierserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.model.Bid;
import via.pro3.datatierserver.model.User;
import via.pro3.datatierserver.repositories.IBidRepository;
import via.pro3.datatierserver.repositories.IUserRepository;

@ExtendWith(MockitoExtension.class)
class BidGrpcServiceTest {

    @Mock
    private IBidRepository bidRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private StreamObserver<DataTierProto.BidResponse> bidResponseObserver;

    @Mock
    private StreamObserver<DataTierProto.DeleteBidResponse> deleteResponseObserver;

    @InjectMocks
    private BidGrpcService bidService;

    //create bid - unsuccessful
    @Test
    void createBid_buyerNotFound_returnsNotFound() {
        DataTierProto.CreateBidRequest request =
                DataTierProto.CreateBidRequest.newBuilder()
                        .setBuyerId(1)
                        .setPropertyId(2)
                        .setAmount(1000)
                        .setExpiryDateSeconds(Instant.now().getEpochSecond())
                        .build();

        when(userRepository.getSingle(1)).thenReturn(Optional.empty());

        bidService.createBid(request, bidResponseObserver);

        verifyGrpcError(bidResponseObserver, Status.NOT_FOUND, "Buyer not found");
        verify(bidRepository, never()).save(any());
    }

    //get bit - not found
    @Test
    void getBid_notFound_returnsNotFound() {
        DataTierProto.GetBidRequest request =
                DataTierProto.GetBidRequest.newBuilder()
                        .setId(99)
                        .build();

        when(bidRepository.getSingle(99)).thenReturn(Optional.empty());

        bidService.getBid(request, bidResponseObserver);

        verifyGrpcError(
                bidResponseObserver,
                Status.NOT_FOUND,
                "Bid with id 99 not found"
        );
    }

    //get bid -successful
    @Test
    void getBid_found_returnsBidResponse() {
        Bid bid = new Bid();
        bid.setId(1);
        bid.setBuyerId(2);
        bid.setPropertyId(3);
        bid.setAmount(BigDecimal.valueOf(500));
        bid.setExpiryDate(Instant.now());
        bid.setStatus("Pending");

        when(bidRepository.getSingle(1)).thenReturn(Optional.of(bid));
        when(userRepository.getSingle(2)).thenReturn(Optional.of(new User())); // for signature check

        DataTierProto.GetBidRequest request =
                DataTierProto.GetBidRequest.newBuilder()
                        .setId(1)
                        .build();

        bidService.getBid(request, bidResponseObserver);

        ArgumentCaptor<DataTierProto.BidResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.BidResponse.class);

        verify(bidResponseObserver).onNext(captor.capture());
        verify(bidResponseObserver).onCompleted();

        assertEquals(1, captor.getValue().getId());
        assertEquals(2, captor.getValue().getBuyerId());
    }

    //delete bid not found
    @Test
    void deleteBid_notFound_returnsNotFound() {
        DataTierProto.DeleteBidRequest request =
                DataTierProto.DeleteBidRequest.newBuilder()
                        .setId(5)
                        .build();

        when(bidRepository.getSingle(5)).thenReturn(Optional.empty());

        bidService.deleteBid(request, deleteResponseObserver);

        verifyGrpcError(
                deleteResponseObserver,
                Status.NOT_FOUND,
                "Bid with id 5 not found"
        );
    }

    //delete bid success
    @Test
    void deleteBid_existingBid_deletesAndReturnsSuccess() {
        Bid bid = new Bid();
        bid.setId(5);

        when(bidRepository.getSingle(5)).thenReturn(Optional.of(bid));

        DataTierProto.DeleteBidRequest request =
                DataTierProto.DeleteBidRequest.newBuilder()
                        .setId(5)
                        .build();

        bidService.deleteBid(request, deleteResponseObserver);

        ArgumentCaptor<DataTierProto.DeleteBidResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.DeleteBidResponse.class);

        verify(bidRepository).deleteById(5);
        verify(deleteResponseObserver).onNext(captor.capture());
        verify(deleteResponseObserver).onCompleted();

        assertEquals(true, captor.getValue().getSuccess());
    }

    //helper methods
    private void verifyGrpcError(
            StreamObserver<?> observer,
            Status expectedStatus,
            String expectedMessage
    ) {
        ArgumentCaptor<Throwable> captor = ArgumentCaptor.forClass(Throwable.class);
        verify(observer).onError(captor.capture());

        StatusRuntimeException ex = (StatusRuntimeException) captor.getValue();
        assertEquals(expectedStatus.getCode(), ex.getStatus().getCode());
        assertEquals(expectedMessage, ex.getStatus().getDescription());
    }
}