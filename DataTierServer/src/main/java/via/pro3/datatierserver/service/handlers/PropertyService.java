package via.pro3.datatierserver.service.property;

import org.springframework.stereotype.Service;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.model.Property;
import via.pro3.datatierserver.repositories.IPropertyRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final IPropertyRepository propertyRepository;

    public PropertyService(IPropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public Property createProperty(DataTierProto.CreatePropertyRequest request) {
        validate(request);

        Property property = new Property();
        property.setAgentId(request.getAgentId());
        property.setTitle(request.getTitle());
        property.setAddress(request.getAddress());
        property.setStartingPrice(BigDecimal.valueOf(request.getStartingPrice()));
        property.setBedrooms(request.getBedrooms());
        property.setBathrooms(request.getBathrooms());
        property.setSizeInSquareFeet(request.getSizeInSquareFeet());
        property.setDescription(request.getDescription());
        property.setStatus("Available");

        return propertyRepository.save(property);
    }

    public Optional<Property> getProperty(long id) {
        return propertyRepository.getSingle(id);
    }

    public List<Property> getProperties() {
        return propertyRepository.getMany();
    }

    public DataTierProto.PropertyResponse toProto(Property property) {
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

    private void validate(DataTierProto.CreatePropertyRequest request) {
        if (request.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (request.getAddress().isBlank()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (request.getStartingPrice() <= 0) {
            throw new IllegalArgumentException("Starting price must be positive");
        }
        if (request.getBedrooms() < 0 || request.getBathrooms() < 0) {
            throw new IllegalArgumentException("Bedrooms/Bathrooms cannot be negative");
        }
        if (request.getSizeInSquareFeet() <= 0) {
            throw new IllegalArgumentException("Size in square feet must be positive");
        }
    }
}