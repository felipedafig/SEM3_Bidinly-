package via.pro3.datatierserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.grpc.DataTierServiceGrpc;
import via.pro3.datatierserver.model.Bid;
import via.pro3.datatierserver.model.Property;
import via.pro3.datatierserver.model.User;
import via.pro3.datatierserver.repositories.IBidRepository;
import via.pro3.datatierserver.repositories.IPropertyRepository;
import via.pro3.datatierserver.repositories.IUserRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class DataTierServiceImpl extends DataTierServiceGrpc.DataTierServiceImplBase {
    
    private static final Logger logger = LoggerFactory.getLogger(DataTierServiceImpl.class);
    
    @Autowired
    private IBidRepository bidRepository;
    
    @Autowired
    private IPropertyRepository propertyRepository;
    
    @Autowired
    private IUserRepository userRepository;
    
    @Override
    public void getBids(DataTierProto.GetBidsRequest request, StreamObserver<DataTierProto.GetBidsResponse> responseObserver) {
        try {
            logger.info("GetBids called - retrieving all bids (no filtering)");
            
            List<Bid> bids = bidRepository.getMany();
            
            logger.info("Found {} bids in database", bids.size());
            
            List<DataTierProto.BidResponse> bidResponses = bids.stream()
                .map(this::convertToBidResponse)
                .collect(Collectors.toList());
            
            DataTierProto.GetBidsResponse response = DataTierProto.GetBidsResponse.newBuilder()
                .addAllBids(bidResponses)
                .build();
            
            logger.info("Sending GetBidsResponse with {} bids", response.getBidsCount());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            logger.error("Error in getBids: {}", e.getMessage(), e);
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving bids: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    private DataTierProto.BidResponse convertToBidResponse(Bid bid) {
        long expiryDateSeconds = bid.getExpiryDate() != null 
            ? bid.getExpiryDate().getEpochSecond() 
            : 0;
        
        return DataTierProto.BidResponse.newBuilder()
            .setId(bid.getId() != null ? bid.getId() : 0)
            .setBuyerId(bid.getBuyerId() != null ? bid.getBuyerId() : 0)
            .setPropertyId(bid.getPropertyId() != null ? bid.getPropertyId() : 0)
            .setAmount(bid.getAmount() != null ? bid.getAmount().doubleValue() : 0.0)
            .setExpiryDateSeconds(expiryDateSeconds)
            .setStatus(bid.getStatus() != null ? bid.getStatus() : "Pending")
            .build();
    }
    
   
}

