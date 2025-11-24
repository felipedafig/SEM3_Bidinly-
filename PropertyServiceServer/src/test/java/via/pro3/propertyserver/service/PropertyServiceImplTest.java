package via.pro3.propertyserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import via.pro3.propertyserver.grpc.PropertyProto;
import via.pro3.propertyserver.model.Property;
import via.pro3.propertyserver.repositories.IPropertyRepository;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

    @Mock
    private IPropertyRepository propertyRepository;

    @Mock
    private StreamObserver<PropertyProto.PropertyResponse> responseObserver;

    private PropertyServiceImpl propertyService;

    @BeforeEach
    void setUp() {
        propertyService = new PropertyServiceImpl(propertyRepository);
    }

    @Test
    void createProperty_success_returnsResponse() {
        PropertyProto.CreatePropertyRequest request = PropertyProto.CreatePropertyRequest.newBuilder()
            .setAgentId(7)
            .setTitle("Loft")
            .setAddress("Main street 1")
            .setStartingPrice(100000)
            .setBedrooms(2)
            .setBathrooms(1)
            .setSizeInSquareFeet(950)
            .build();

        Property saved = new Property();
        saved.setId(42);
        saved.setAgentId(7);
        saved.setTitle("Loft");
        saved.setAddress("Main street 1");
        saved.setStartingPrice(BigDecimal.valueOf(100000));
        saved.setBedrooms(2);
        saved.setBathrooms(1);
        saved.setSizeInSquareFeet(950.0);
        saved.setStatus("Available");

        when(propertyRepository.save(any(Property.class))).thenReturn(saved);

        propertyService.createProperty(request, responseObserver);

        ArgumentCaptor<PropertyProto.PropertyResponse> responseCaptor = ArgumentCaptor.forClass(PropertyProto.PropertyResponse.class);
        verify(responseObserver).onNext(responseCaptor.capture());
        verify(responseObserver).onCompleted();

        PropertyProto.PropertyResponse response = responseCaptor.getValue();
        assertEquals(42, response.getId());
        assertEquals("Loft", response.getTitle());
        assertEquals(100000, response.getStartingPrice());
        assertEquals(950, response.getSizeInSquareFeet());

        ArgumentCaptor<Property> entityCaptor = ArgumentCaptor.forClass(Property.class);
        verify(propertyRepository).save(entityCaptor.capture());
        assertEquals("Loft", entityCaptor.getValue().getTitle());
    }

    @Test
    void createProperty_invalidArgument_returnsGrpcError() {
        PropertyProto.CreatePropertyRequest request = PropertyProto.CreatePropertyRequest.newBuilder()
            .setAgentId(7)
            .setTitle("")
            .setAddress("Main street 1")
            .setStartingPrice(0)
            .setBedrooms(0)
            .setBathrooms(0)
            .setSizeInSquareFeet(0)
            .build();

        propertyService.createProperty(request, responseObserver);

        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        StatusRuntimeException statusException = (StatusRuntimeException) errorCaptor.getValue();
        assertEquals(Status.INVALID_ARGUMENT.getCode(), statusException.getStatus().getCode());
        assertEquals("Title is required", statusException.getStatus().getDescription());
        verify(propertyRepository, never()).save(any());
    }

    @Test
    void getProperty_notFound_returnsGrpcError() {
        PropertyProto.GetPropertyRequest request = PropertyProto.GetPropertyRequest.newBuilder()
            .setId(99)
            .build();

        when(propertyRepository.getSingle(99)).thenReturn(Optional.empty());

        propertyService.getProperty(request, responseObserver);

        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        StatusRuntimeException statusException = (StatusRuntimeException) errorCaptor.getValue();
        assertEquals(Status.NOT_FOUND.getCode(), statusException.getStatus().getCode());
        assertEquals("Property with id 99 not found", statusException.getStatus().getDescription());
    }
}