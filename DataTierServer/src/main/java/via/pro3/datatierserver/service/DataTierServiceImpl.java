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
    
    // Placeholder implementations for other methods
    @Override
    public void getBid(DataTierProto.GetBidRequest request, StreamObserver<DataTierProto.BidResponse> responseObserver) {
        try {
            logger.info("GetBid called with id: {}", request.getId());
            
            var bidOpt = bidRepository.getSingle(request.getId());
            if (bidOpt.isEmpty()) {
                logger.warn("Bid with id {} not found", request.getId());
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Bid with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            Bid bid = bidOpt.get();
            DataTierProto.BidResponse response = convertToBidResponse(bid);
            
            logger.info("Sending BidResponse for bid id: {}", bid.getId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            logger.error("Error in getBid: {}", e.getMessage(), e);
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    @Override
    public void getProperty(DataTierProto.GetPropertyRequest request, StreamObserver<DataTierProto.PropertyResponse> responseObserver) {
        try {
            logger.info("GetProperty called with id: {}", request.getId());
            
            var propertyOpt = propertyRepository.getSingle(request.getId());
            if (propertyOpt.isEmpty()) {
                logger.warn("Property with id {} not found", request.getId());
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Property with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            var property = propertyOpt.get();
            DataTierProto.PropertyResponse response = DataTierProto.PropertyResponse.newBuilder()
                .setId(property.getId() != null ? property.getId() : 0)
                .setAgentId(property.getAgentId() != null ? property.getAgentId() : 0)
                .setTitle(property.getTitle() != null ? property.getTitle() : "")
                .setAddress(property.getAddress() != null ? property.getAddress() : "")
                .setStartingPrice(property.getStartingPrice() != null ? property.getStartingPrice().doubleValue() : 0.0)
                .setBedrooms(property.getBedrooms() != null ? property.getBedrooms() : 0)
                .setBathrooms(property.getBathrooms() != null ? property.getBathrooms() : 0)
                .setSizeInSquareFeet(property.getSizeInSquareFeet() != null ? property.getSizeInSquareFeet().intValue() : 0)
                .setDescription(property.getDescription() != null ? property.getDescription() : "")
                .setStatus(property.getStatus() != null ? property.getStatus() : "Available")
                .build();
            
            logger.info("Sending PropertyResponse for property id: {}", property.getId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            logger.error("Error in getProperty: {}", e.getMessage(), e);
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    @Override
    public void getUser(DataTierProto.GetUserRequest request, StreamObserver<DataTierProto.UserResponse> responseObserver) {
        try {
            logger.info("GetUser called with id: {}", request.getId());
            
            var userOpt = userRepository.getSingle(request.getId());
            if (userOpt.isEmpty()) {
                logger.warn("User with id {} not found", request.getId());
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("User with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            var user = userOpt.get();
            DataTierProto.UserResponse response = DataTierProto.UserResponse.newBuilder()
                .setId(user.getId() != null ? user.getId() : 0)
                .setUsername(user.getUsername() != null ? user.getUsername() : "")
                .setRoleId(user.getRoleId() != null ? user.getRoleId() : 0)
                .build();
            
            logger.info("Sending UserResponse for user id: {}", user.getId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            logger.error("Error in getUser: {}", e.getMessage(), e);
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    // Add other method stubs as needed...
}

