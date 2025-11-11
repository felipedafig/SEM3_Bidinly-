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
import via.pro3.datatierserver.model.Sale;
import via.pro3.datatierserver.model.User;
import via.pro3.datatierserver.repositories.IBidRepository;
import via.pro3.datatierserver.repositories.IPropertyRepository;
import via.pro3.datatierserver.repositories.ISaleRepository;
import via.pro3.datatierserver.repositories.IUserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
    
    @Autowired
    private ISaleRepository saleRepository;
    
    @Override
    public void getBids(DataTierProto.GetBidsRequest request, StreamObserver<DataTierProto.GetBidsResponse> responseObserver) {
        try {
            logger.info("GetBids called - retrieving all bids");
            
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
    
    @Override
    public void getProperty(DataTierProto.GetPropertyRequest request, StreamObserver<DataTierProto.PropertyResponse> responseObserver) {
        try {
            logger.info("GetProperty called - id: {}", request.getId());
            
            Optional<Property> propertyOpt = propertyRepository.getSingle(request.getId());
            
            if (propertyOpt.isEmpty()) {
                logger.warn("Property with id {} not found", request.getId());
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Property with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            Property property = propertyOpt.get();
            DataTierProto.PropertyResponse response = convertToPropertyResponse(property);
            
            logger.info("Sending PropertyResponse for property id: {}", property.getId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            logger.error("Error in getProperty: {}", e.getMessage(), e);
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving property: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    private DataTierProto.PropertyResponse convertToPropertyResponse(Property property) {
        return DataTierProto.PropertyResponse.newBuilder()
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
    }
    
    @Override
    public void getUser(DataTierProto.GetUserRequest request, StreamObserver<DataTierProto.UserResponse> responseObserver) {
        try {
            logger.info("GetUser called - id: {}", request.getId());
            
            Optional<User> userOpt = userRepository.getSingle(request.getId());
            
            if (userOpt.isEmpty()) {
                logger.warn("User with id {} not found", request.getId());
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("User with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            User user = userOpt.get();
            DataTierProto.UserResponse response = convertToUserResponse(user);
            
            logger.info("Sending UserResponse for user id: {}", user.getId());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            logger.error("Error in getUser: {}", e.getMessage(), e);
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving user: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    private DataTierProto.UserResponse convertToUserResponse(User user) {
        return DataTierProto.UserResponse.newBuilder()
            .setId(user.getId() != null ? user.getId() : 0)
            .setUsername(user.getUsername() != null ? user.getUsername() : "")
            .setRoleId(user.getRoleId() != null ? user.getRoleId() : 0)
            .build();
    }
    
    @Override
    public void getSales(DataTierProto.GetSalesRequest request, StreamObserver<DataTierProto.GetSalesResponse> responseObserver) {
        try {
            logger.info("GetSales called - retrieving all sales");
            
            List<Sale> sales = saleRepository.findAll();
            
            logger.info("Found {} sales in database", sales.size());
            
            List<DataTierProto.SaleResponse> saleResponses = sales.stream()
                .map(this::convertToSaleResponse)
                .collect(Collectors.toList());
            
            DataTierProto.GetSalesResponse response = DataTierProto.GetSalesResponse.newBuilder()
                .addAllSales(saleResponses)
                .build();
            
            logger.info("Sending GetSalesResponse with {} sales", response.getSalesCount());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            logger.error("Error in getSales: {}", e.getMessage(), e);
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving sales: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    private DataTierProto.SaleResponse convertToSaleResponse(Sale sale) {
        long timeOfSaleSeconds = sale.getTimeOfSale() != null 
            ? sale.getTimeOfSale().getEpochSecond() 
            : 0;
        
        return DataTierProto.SaleResponse.newBuilder()
            .setId(sale.getId() != null ? sale.getId() : 0)
            .setTimeOfSaleSeconds(timeOfSaleSeconds)
            .setPropertyId(sale.getPropertyId() != null ? sale.getPropertyId() : 0)
            .setBidId(sale.getBidId() != null ? sale.getBidId() : 0)
            .setBuyerId(sale.getBuyerId() != null ? sale.getBuyerId() : 0)
            .setAgentId(sale.getAgentId() != null ? sale.getAgentId() : 0)
            .build();
    }
    
   
}

