package via.pro3.datatierserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.grpc.PropertyServiceGrpc;
import via.pro3.datatierserver.model.Property;
import via.pro3.datatierserver.repositories.IPropertyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@GrpcService
public class PropertyGrpcService extends PropertyServiceGrpc.PropertyServiceImplBase{

    @Autowired
    private IPropertyRepository propertyRepository;

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

            //kjkj

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
}
