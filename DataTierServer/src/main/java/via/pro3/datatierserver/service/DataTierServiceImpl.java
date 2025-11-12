package via.pro3.datatierserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.grpc.DataTierServiceGrpc;
import via.pro3.datatierserver.model.Bid;
import via.pro3.datatierserver.model.Property;
import via.pro3.datatierserver.model.Sale;
import via.pro3.datatierserver.model.User;
import via.pro3.datatierserver.repositories.IBidRepository;
import via.pro3.datatierserver.repositories.IPropertyRepository;
import via.pro3.datatierserver.repositories.IRoleRepository;
import via.pro3.datatierserver.repositories.ISaleRepository;
import via.pro3.datatierserver.repositories.IUserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@GrpcService
public class DataTierServiceImpl extends DataTierServiceGrpc.DataTierServiceImplBase {
    
    @Autowired
    private IBidRepository bidRepository;
    
    @Autowired
    private IPropertyRepository propertyRepository;
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private IRoleRepository roleRepository;
    
    @Autowired
    private ISaleRepository saleRepository;
    
    @Override
    public void getBids(DataTierProto.GetBidsRequest request, StreamObserver<DataTierProto.GetBidsResponse> responseObserver) {
        try {
            List<Bid> bids = bidRepository.getMany();
            
            updateExpiredBids(bids);
            
            List<DataTierProto.BidResponse> bidResponses = bids.stream()
                .map(this::convertToBidResponse)
                .collect(Collectors.toList());
            
            DataTierProto.GetBidsResponse response = DataTierProto.GetBidsResponse.newBuilder()
                .addAllBids(bidResponses)
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving bids: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    @Override
    public void getBid(DataTierProto.GetBidRequest request, StreamObserver<DataTierProto.BidResponse> responseObserver) {
        try {
            Optional<Bid> bidOpt = bidRepository.getSingle(request.getId());
            
            if (bidOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Bid with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            Bid bid = bidOpt.get();
            
            updateExpiredBid(bid);
            
            DataTierProto.BidResponse response = convertToBidResponse(bid);
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving bid: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    private void updateExpiredBids(List<Bid> bids) {
        for (Bid bid : bids) {
            updateExpiredBid(bid);
        }
    }
    
    private boolean updateExpiredBid(Bid bid) {
        Instant now = Instant.now();
        
        if (bid.getExpiryDate() != null 
            && bid.getExpiryDate().isBefore(now) 
            && "Pending".equals(bid.getStatus())) {
            
            bid.setStatus("Expired");
            bidRepository.save(bid);
            return true;
        }
        
        return false;
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
            Optional<Property> propertyOpt = propertyRepository.getSingle(request.getId());
            
            if (propertyOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Property with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            Property property = propertyOpt.get();
            DataTierProto.PropertyResponse response = convertToPropertyResponse(property);
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving property: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    @Override
    public void getProperties(DataTierProto.GetPropertiesRequest request, StreamObserver<DataTierProto.GetPropertiesResponse> responseObserver) {
        try {
            List<Property> properties = propertyRepository.getMany();
            
            List<DataTierProto.PropertyResponse> propertyResponses = properties.stream()
                .map(this::convertToPropertyResponse)
                .collect(Collectors.toList());
            
            DataTierProto.GetPropertiesResponse response = DataTierProto.GetPropertiesResponse.newBuilder()
                .addAllProperties(propertyResponses)
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error retrieving properties: " + e.getMessage())
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
            Optional<User> userOpt = userRepository.getSingle(request.getId());
            
            if (userOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("User with id " + request.getId() + " not found")
                    .asRuntimeException());
                return;
            }
            
            User user = userOpt.get();
            DataTierProto.UserResponse response = convertToUserResponse(user);
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
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
    public void createUser(DataTierProto.CreateUserRequest request, StreamObserver<DataTierProto.UserResponse> responseObserver) {
        try {
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                    .withDescription("Username is required")
                    .asRuntimeException());
                return;
            }
            
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                    .withDescription("Password is required")
                    .asRuntimeException());
                return;
            }
            
            Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
            if (existingUser.isPresent()) {
                responseObserver.onError(io.grpc.Status.ALREADY_EXISTS
                    .withDescription("Username already exists: " + request.getUsername())
                    .asRuntimeException());
                return;
            }
            
           
            User newUser = new User();
            newUser.setUsername(request.getUsername());
            newUser.setPassword(request.getPassword()); 
            
            User savedUser = userRepository.save(newUser);
            
            DataTierProto.UserResponse response = convertToUserResponse(savedUser);
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (io.grpc.StatusRuntimeException e) {
            responseObserver.onError(e);
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error creating user: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    @Override
    public void getSales(DataTierProto.GetSalesRequest request, StreamObserver<DataTierProto.GetSalesResponse> responseObserver) {
        try {
            List<Sale> sales = saleRepository.findAll();
            
            List<DataTierProto.SaleResponse> saleResponses = sales.stream()
                .map(this::convertToSaleResponse)
                .collect(Collectors.toList());
            
            DataTierProto.GetSalesResponse response = DataTierProto.GetSalesResponse.newBuilder()
                .addAllSales(saleResponses)
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
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

