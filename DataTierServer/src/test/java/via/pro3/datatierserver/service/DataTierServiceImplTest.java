package via.pro3.datatierserver.service;

import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.model.Property;
import via.pro3.datatierserver.model.User;
import via.pro3.datatierserver.repositories.IPropertyRepository;
import via.pro3.datatierserver.repositories.IUserRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataTierServiceImplTest {

    @Mock
    private IPropertyRepository propertyRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private StreamObserver<DataTierProto.PropertyResponse> responseObserver;

    @InjectMocks
    private DataTierServiceImpl dataTierService;

    private DataTierProto.CreatePropertyRequest validRequest;
    private User validAgent;
    private Property savedProperty;

    @BeforeEach
    void setUp() {
        //request
        validRequest = DataTierProto.CreatePropertyRequest.newBuilder()
                .setAgentId(1)
                .setTitle("Luxury Downtown Apartment")
                .setAddress("123 Main Street")
                .setStartingPrice(450000.00)
                .setBedrooms(2)
                .setBathrooms(2)
                .setSizeInSquareFeet(1200)
                .setDescription("Beautiful apartment with stunning city views")
                .setStatus("Available")
                .build();

        //agent
        validAgent = new User();
        validAgent.setId(1);
        validAgent.setUsername("agent_john");
        validAgent.setPassword("password123");
        validAgent.setRoleId(1);

        //property
        savedProperty = new Property();
        savedProperty.setId(1);
        savedProperty.setAgentId(1);
        savedProperty.setTitle("Luxury Downtown Apartment");
        savedProperty.setAddress("123 Main Street");
        savedProperty.setStartingPrice(BigDecimal.valueOf(450000.00));
        savedProperty.setBedrooms(2);
        savedProperty.setBathrooms(2);
        savedProperty.setSizeInSquareFeet(1200.0);
        savedProperty.setDescription("Beautiful apartment with stunning city views");
        savedProperty.setStatus("Available");
    }

    @Test
    void createProperty_Success_ReturnsPropertyResponse() {
        // Arrange
        when(userRepository.getSingle(1)).thenReturn(Optional.of(validAgent));
        when(propertyRepository.save(any(Property.class))).thenReturn(savedProperty);

        // Act
        dataTierService.createProperty(validRequest, responseObserver);

        // Assert
        ArgumentCaptor<DataTierProto.PropertyResponse> responseCaptor = 
                ArgumentCaptor.forClass(DataTierProto.PropertyResponse.class);
        verify(responseObserver).onNext(responseCaptor.capture());
        verify(responseObserver).onCompleted();
        verify(responseObserver, never()).onError(any());

        DataTierProto.PropertyResponse response = responseCaptor.getValue();
        assertEquals(1, response.getId());
        assertEquals(1, response.getAgentId());
        assertEquals("Luxury Downtown Apartment", response.getTitle());
        assertEquals("123 Main Street", response.getAddress());
        assertEquals(450000.00, response.getStartingPrice(), 0.01);
        assertEquals(2, response.getBedrooms());
        assertEquals(2, response.getBathrooms());
        assertEquals(1200, response.getSizeInSquareFeet());
        assertEquals("Beautiful apartment with stunning city views", response.getDescription());
        assertEquals("Available", response.getStatus());

        //repository interactions
        ArgumentCaptor<Property> propertyCaptor = ArgumentCaptor.forClass(Property.class);
        verify(propertyRepository).save(propertyCaptor.capture());
        Property capturedProperty = propertyCaptor.getValue();
        assertEquals(1, capturedProperty.getAgentId());
        assertEquals("Luxury Downtown Apartment", capturedProperty.getTitle());
    }

    @Test
    void createProperty_InvalidAgentId_ThrowsNotFound() {
        // Arrange
        when(userRepository.getSingle(999)).thenReturn(Optional.empty());

        DataTierProto.CreatePropertyRequest request = validRequest.toBuilder()
                .setAgentId(999)
                .build();

        // Act
        dataTierService.createProperty(request, responseObserver);

        // Assert
        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        Throwable error = errorCaptor.getValue();
        assertTrue(error instanceof io.grpc.StatusRuntimeException);
        io.grpc.StatusRuntimeException grpcError = (io.grpc.StatusRuntimeException) error;
        assertEquals(io.grpc.Status.NOT_FOUND.getCode(), grpcError.getStatus().getCode());
        String description = grpcError.getStatus().getDescription();
        assertNotNull(description);
        assertTrue(description.contains("Agent with id 999 not found"));

        verify(propertyRepository, never()).save(any());
    }

    @Test
    void createProperty_NullTitle_ThrowsInvalidArgument() {
        // Arrange
        when(userRepository.getSingle(1)).thenReturn(Optional.of(validAgent));

        DataTierProto.CreatePropertyRequest request = validRequest.toBuilder()
                .setTitle("")
                .build();

        // Act
        dataTierService.createProperty(request, responseObserver);

        // Assert
        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        Throwable error = errorCaptor.getValue();
        assertTrue(error instanceof io.grpc.StatusRuntimeException);
        io.grpc.StatusRuntimeException grpcError = (io.grpc.StatusRuntimeException) error;
        assertEquals(io.grpc.Status.INVALID_ARGUMENT.getCode(), grpcError.getStatus().getCode());
        String description = grpcError.getStatus().getDescription();
        assertNotNull(description);
        assertTrue(description.contains("Title is required"));

        verify(propertyRepository, never()).save(any());
    }

    @Test
    void createProperty_ZeroStartingPrice_ThrowsInvalidArgument() {
        // Arrange
        when(userRepository.getSingle(1)).thenReturn(Optional.of(validAgent));

        DataTierProto.CreatePropertyRequest request = validRequest.toBuilder()
                .setStartingPrice(0.0)
                .build();

        // Act
        dataTierService.createProperty(request, responseObserver);

        // Assert
        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        Throwable error = errorCaptor.getValue();
        assertTrue(error instanceof io.grpc.StatusRuntimeException);
        io.grpc.StatusRuntimeException grpcError = (io.grpc.StatusRuntimeException) error;
        assertEquals(io.grpc.Status.INVALID_ARGUMENT.getCode(), grpcError.getStatus().getCode());
        String description = grpcError.getStatus().getDescription();
        assertNotNull(description);
        assertTrue(description.contains("Starting price must be greater than zero"));

        verify(propertyRepository, never()).save(any());
    }

    @Test
    void createProperty_NegativeStartingPrice_ThrowsInvalidArgument() {
        // Arrange
        when(userRepository.getSingle(1)).thenReturn(Optional.of(validAgent));

        DataTierProto.CreatePropertyRequest request = validRequest.toBuilder()
                .setStartingPrice(-1000.0)
                .build();

        // Act
        dataTierService.createProperty(request, responseObserver);

        // Assert
        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        Throwable error = errorCaptor.getValue();
        assertTrue(error instanceof io.grpc.StatusRuntimeException);
        io.grpc.StatusRuntimeException grpcError = (io.grpc.StatusRuntimeException) error;
        assertEquals(io.grpc.Status.INVALID_ARGUMENT.getCode(), grpcError.getStatus().getCode());
        String description = grpcError.getStatus().getDescription();
        assertNotNull(description);
        assertTrue(description.contains("Starting price must be greater than zero"));

        verify(propertyRepository, never()).save(any());
    }

    @Test
    void createProperty_ZeroBedrooms_ThrowsInvalidArgument() {
        // Arrange
        when(userRepository.getSingle(1)).thenReturn(Optional.of(validAgent));

        DataTierProto.CreatePropertyRequest request = validRequest.toBuilder()
                .setBedrooms(0)
                .build();

        // Act
        dataTierService.createProperty(request, responseObserver);

        // Assert
        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        Throwable error = errorCaptor.getValue();
        assertTrue(error instanceof io.grpc.StatusRuntimeException);
        io.grpc.StatusRuntimeException grpcError = (io.grpc.StatusRuntimeException) error;
        assertEquals(io.grpc.Status.INVALID_ARGUMENT.getCode(), grpcError.getStatus().getCode());
        String description = grpcError.getStatus().getDescription();
        assertNotNull(description);
        assertTrue(description.contains("Bedrooms must be greater than zero"));

        verify(propertyRepository, never()).save(any());
    }

    @Test
    void createProperty_ZeroBathrooms_ThrowsInvalidArgument() {
        // Arrange
        when(userRepository.getSingle(1)).thenReturn(Optional.of(validAgent));

        DataTierProto.CreatePropertyRequest request = validRequest.toBuilder()
                .setBathrooms(0)
                .build();

        // Act
        dataTierService.createProperty(request, responseObserver);

        // Assert
        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        Throwable error = errorCaptor.getValue();
        assertTrue(error instanceof io.grpc.StatusRuntimeException);
        io.grpc.StatusRuntimeException grpcError = (io.grpc.StatusRuntimeException) error;
        assertEquals(io.grpc.Status.INVALID_ARGUMENT.getCode(), grpcError.getStatus().getCode());
        String description = grpcError.getStatus().getDescription();
        assertNotNull(description);
        assertTrue(description.contains("Bathrooms must be greater than zero"));

        verify(propertyRepository, never()).save(any());
    }

    @Test
    void createProperty_ZeroSizeInSquareFeet_ThrowsInvalidArgument() {
        // Arrange
        when(userRepository.getSingle(1)).thenReturn(Optional.of(validAgent));

        DataTierProto.CreatePropertyRequest request = validRequest.toBuilder()
                .setSizeInSquareFeet(0)
                .build();

        // Act
        dataTierService.createProperty(request, responseObserver);

        // Assert
        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        Throwable error = errorCaptor.getValue();
        assertTrue(error instanceof io.grpc.StatusRuntimeException);
        io.grpc.StatusRuntimeException grpcError = (io.grpc.StatusRuntimeException) error;
        assertEquals(io.grpc.Status.INVALID_ARGUMENT.getCode(), grpcError.getStatus().getCode());
        String description = grpcError.getStatus().getDescription();
        assertNotNull(description);
        assertTrue(description.contains("Size in square feet must be greater than zero"));

        verify(propertyRepository, never()).save(any());
    }

    @Test
    void createProperty_DatabaseError_ThrowsInternalError() {
        // Arrange
        when(userRepository.getSingle(1)).thenReturn(Optional.of(validAgent));
        when(propertyRepository.save(any(Property.class)))
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act
        dataTierService.createProperty(validRequest, responseObserver);

        // Assert
        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        Throwable error = errorCaptor.getValue();
        assertTrue(error instanceof io.grpc.StatusRuntimeException);
        io.grpc.StatusRuntimeException grpcError = (io.grpc.StatusRuntimeException) error;
        assertEquals(io.grpc.Status.INTERNAL.getCode(), grpcError.getStatus().getCode());
        String description = grpcError.getStatus().getDescription();
        assertNotNull(description);
        assertTrue(description.contains("Error creating property"));
        assertTrue(description.contains("Database connection failed"));
    }

    @Test
    void createProperty_UserRepositoryError_ThrowsInternalError() {
        // Arrange
        when(userRepository.getSingle(1))
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act
        dataTierService.createProperty(validRequest, responseObserver);

        // Assert
        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        Throwable error = errorCaptor.getValue();
        assertTrue(error instanceof io.grpc.StatusRuntimeException);
        io.grpc.StatusRuntimeException grpcError = (io.grpc.StatusRuntimeException) error;
        assertEquals(io.grpc.Status.INTERNAL.getCode(), grpcError.getStatus().getCode());
        String description = grpcError.getStatus().getDescription();
        assertNotNull(description);
        assertTrue(description.contains("Error creating property"));

        verify(propertyRepository, never()).save(any());
    }

    @Test
    void createProperty_DefaultStatusWhenNotProvided_SetsAvailable() {
        // Arrange
        when(userRepository.getSingle(1)).thenReturn(Optional.of(validAgent));
        
        Property propertyWithDefaultStatus = new Property();
        propertyWithDefaultStatus.setId(1);
        propertyWithDefaultStatus.setAgentId(1);
        propertyWithDefaultStatus.setTitle("Luxury Downtown Apartment");
        propertyWithDefaultStatus.setAddress("123 Main Street");
        propertyWithDefaultStatus.setStartingPrice(BigDecimal.valueOf(450000.00));
        propertyWithDefaultStatus.setBedrooms(2);
        propertyWithDefaultStatus.setBathrooms(2);
        propertyWithDefaultStatus.setSizeInSquareFeet(1200.0);
        propertyWithDefaultStatus.setDescription("Beautiful apartment with stunning city views");
        propertyWithDefaultStatus.setStatus("Available");

        when(propertyRepository.save(any(Property.class))).thenReturn(propertyWithDefaultStatus);

        DataTierProto.CreatePropertyRequest request = validRequest.toBuilder()
                .clearStatus() // Status not provided
                .build();

        // Act
        dataTierService.createProperty(request, responseObserver);

        // Assert
        ArgumentCaptor<Property> propertyCaptor = ArgumentCaptor.forClass(Property.class);
        verify(propertyRepository).save(propertyCaptor.capture());
        Property capturedProperty = propertyCaptor.getValue();
        assertEquals("Available", capturedProperty.getStatus());
    }

    @Test
    void createProperty_OptionalFieldsCanBeNull_StillCreatesProperty() {
        // Arrange
        when(userRepository.getSingle(1)).thenReturn(Optional.of(validAgent));
        
        Property propertyWithoutOptionalFields = new Property();
        propertyWithoutOptionalFields.setId(1);
        propertyWithoutOptionalFields.setAgentId(1);
        propertyWithoutOptionalFields.setTitle("Minimal Property");
        propertyWithoutOptionalFields.setAddress(null);
        propertyWithoutOptionalFields.setStartingPrice(BigDecimal.valueOf(100000.00));
        propertyWithoutOptionalFields.setBedrooms(1);
        propertyWithoutOptionalFields.setBathrooms(1);
        propertyWithoutOptionalFields.setSizeInSquareFeet(500.0);
        propertyWithoutOptionalFields.setDescription(null);
        propertyWithoutOptionalFields.setStatus("Available");

        when(propertyRepository.save(any(Property.class))).thenReturn(propertyWithoutOptionalFields);

        DataTierProto.CreatePropertyRequest request = DataTierProto.CreatePropertyRequest.newBuilder()
                .setAgentId(1)
                .setTitle("Minimal Property")
                .setStartingPrice(100000.00)
                .setBedrooms(1)
                .setBathrooms(1)
                .setSizeInSquareFeet(500)
                .build();

        // Act
        dataTierService.createProperty(request, responseObserver);

        // Assert
        ArgumentCaptor<DataTierProto.PropertyResponse> responseCaptor = 
                ArgumentCaptor.forClass(DataTierProto.PropertyResponse.class);
        verify(responseObserver).onNext(responseCaptor.capture());
        verify(responseObserver).onCompleted();

        DataTierProto.PropertyResponse response = responseCaptor.getValue();
        assertEquals("Minimal Property", response.getTitle());
        assertEquals("", response.getAddress()); // Empty string when null
        assertEquals("", response.getDescription()); // Empty string when null
    }
}

