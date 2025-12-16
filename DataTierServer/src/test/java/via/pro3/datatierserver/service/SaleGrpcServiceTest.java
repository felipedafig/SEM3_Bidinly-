package via.pro3.datatierserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.model.Sale;
import via.pro3.datatierserver.repositories.ISaleRepository;

@ExtendWith(MockitoExtension.class)
class SaleGrpcServiceTest {

    @Mock
    private ISaleRepository saleRepository;

    @Mock
    private StreamObserver<DataTierProto.SaleResponse> saleResponseObserver;

    @Mock
    private StreamObserver<DataTierProto.DeleteSaleResponse> deleteResponseObserver;

    @InjectMocks
    private SaleGrpcService saleService;

    @Test
    void createSale_validRequest_savesAndReturnsResponse() {
        Sale saved = new Sale();
        saved.setId(1);
        saved.setPropertyId(2);
        saved.setBidId(3);
        saved.setBuyerId(4);
        saved.setAgentId(5);
        saved.setTimeOfSale(Instant.now());

        when(saleRepository.save(any(Sale.class)))
                .thenReturn(saved);

        DataTierProto.CreateSaleRequest request =
                DataTierProto.CreateSaleRequest.newBuilder()
                        .setTimeOfSaleSeconds(Instant.now().getEpochSecond())
                        .setPropertyId(2)
                        .setBidId(3)
                        .setBuyerId(4)
                        .setAgentId(5)
                        .build();

        saleService.createSale(request, saleResponseObserver);

        ArgumentCaptor<DataTierProto.SaleResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.SaleResponse.class);

        verify(saleResponseObserver).onNext(captor.capture());
        verify(saleResponseObserver).onCompleted();

        assertEquals(1, captor.getValue().getId());
        assertEquals(2, captor.getValue().getPropertyId());
    }

    @Test
    void getSale_notFound_returnsNotFound() {
        when(saleRepository.findById(99))
                .thenReturn(Optional.empty());

        DataTierProto.GetSaleRequest request =
                DataTierProto.GetSaleRequest.newBuilder()
                        .setId(99)
                        .build();

        saleService.getSale(request, saleResponseObserver);

        verifyGrpcError(
                saleResponseObserver,
                Status.NOT_FOUND,
                "Sale with id 99 not found"
        );
    }

    @Test
    void getSale_found_returnsResponse() {
        Sale sale = new Sale();
        sale.setId(10);
        sale.setPropertyId(20);

        when(saleRepository.findById(10))
                .thenReturn(Optional.of(sale));

        DataTierProto.GetSaleRequest request =
                DataTierProto.GetSaleRequest.newBuilder()
                        .setId(10)
                        .build();

        saleService.getSale(request, saleResponseObserver);

        ArgumentCaptor<DataTierProto.SaleResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.SaleResponse.class);

        verify(saleResponseObserver).onNext(captor.capture());
        verify(saleResponseObserver).onCompleted();

        assertEquals(10, captor.getValue().getId());
    }

    @Test
    void updateSale_notFound_returnsNotFound() {
        when(saleRepository.findById(5))
                .thenReturn(Optional.empty());

        DataTierProto.UpdateSaleRequest request =
                DataTierProto.UpdateSaleRequest.newBuilder()
                        .setId(5)
                        .build();

        saleService.updateSale(request, saleResponseObserver);

        verifyGrpcError(
                saleResponseObserver,
                Status.NOT_FOUND,
                "Sale with id 5 not found"
        );
    }

    @Test
    void updateSale_found_updatesAndReturnsResponse() {
        Sale existing = new Sale();
        existing.setId(5);

        Sale updated = new Sale();
        updated.setId(5);
        updated.setPropertyId(99);

        when(saleRepository.findById(5))
                .thenReturn(Optional.of(existing));
        when(saleRepository.save(any(Sale.class)))
                .thenReturn(updated);

        DataTierProto.UpdateSaleRequest request =
                DataTierProto.UpdateSaleRequest.newBuilder()
                        .setId(5)
                        .setPropertyId(99)
                        .build();

        saleService.updateSale(request, saleResponseObserver);

        ArgumentCaptor<DataTierProto.SaleResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.SaleResponse.class);

        verify(saleResponseObserver).onNext(captor.capture());
        verify(saleResponseObserver).onCompleted();

        assertEquals(99, captor.getValue().getPropertyId());
    }

    @Test
    void deleteSale_notFound_returnsNotFound() {
        when(saleRepository.findById(7))
                .thenReturn(Optional.empty());

        DataTierProto.DeleteSaleRequest request =
                DataTierProto.DeleteSaleRequest.newBuilder()
                        .setId(7)
                        .build();

        saleService.deleteSale(request, deleteResponseObserver);

        verifyGrpcError(
                deleteResponseObserver,
                Status.NOT_FOUND,
                "Sale with id 7 not found"
        );
    }

    @Test
    void deleteSale_found_deletesAndReturnsSuccess() {
        Sale sale = new Sale();
        sale.setId(7);

        when(saleRepository.findById(7))
                .thenReturn(Optional.of(sale));

        DataTierProto.DeleteSaleRequest request =
                DataTierProto.DeleteSaleRequest.newBuilder()
                        .setId(7)
                        .build();

        saleService.deleteSale(request, deleteResponseObserver);

        ArgumentCaptor<DataTierProto.DeleteSaleResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.DeleteSaleResponse.class);

        verify(saleRepository).deleteById(7);
        verify(deleteResponseObserver).onNext(captor.capture());
        verify(deleteResponseObserver).onCompleted();

        assertEquals(true, captor.getValue().getSuccess());
    }

    //helpers
    private void verifyGrpcError(
            StreamObserver<?> observer,
            Status expectedStatus,
            String expectedMessage
    ) {
        ArgumentCaptor<Throwable> captor =
                ArgumentCaptor.forClass(Throwable.class);

        verify(observer).onError(captor.capture());

        StatusRuntimeException ex =
                (StatusRuntimeException) captor.getValue();

        assertEquals(expectedStatus.getCode(), ex.getStatus().getCode());
        assertEquals(expectedMessage, ex.getStatus().getDescription());
    }
}