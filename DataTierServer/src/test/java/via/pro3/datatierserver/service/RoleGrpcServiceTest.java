package via.pro3.datatierserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.model.Role;
import via.pro3.datatierserver.repositories.IRoleRepository;

@ExtendWith(MockitoExtension.class)
class RoleGrpcServiceTest {

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private StreamObserver<DataTierProto.GetRoleResponse> responseObserver;

    @InjectMocks
    private RoleGrpcService roleService;

    @Test
    void getRole_notFound_returnsNotFound() {
        when(roleRepository.getSingle(99))
                .thenReturn(Optional.empty());

        DataTierProto.GetRoleRequest request =
                DataTierProto.GetRoleRequest.newBuilder()
                        .setRoleId(99)
                        .build();

        roleService.getRole(request, responseObserver);

        ArgumentCaptor<Throwable> errorCaptor =
                ArgumentCaptor.forClass(Throwable.class);

        verify(responseObserver).onError(errorCaptor.capture());

        StatusRuntimeException ex =
                (StatusRuntimeException) errorCaptor.getValue();

        assertEquals(Status.NOT_FOUND.getCode(), ex.getStatus().getCode());
        assertEquals(
                "Role with id 99 not found",
                ex.getStatus().getDescription()
        );
    }

    @Test
    void getRole_found_returnsRoleName() {
        Role role = new Role();
        role.setId(1);
        role.setName("Admin");

        when(roleRepository.getSingle(1))
                .thenReturn(Optional.of(role));

        DataTierProto.GetRoleRequest request =
                DataTierProto.GetRoleRequest.newBuilder()
                        .setRoleId(1)
                        .build();

        roleService.getRole(request, responseObserver);

        ArgumentCaptor<DataTierProto.GetRoleResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.GetRoleResponse.class);

        verify(responseObserver).onNext(captor.capture());
        verify(responseObserver).onCompleted();

        assertEquals("Admin", captor.getValue().getRoleName());
    }
}