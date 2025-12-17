package via.pro3.datatierserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
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
import via.pro3.datatierserver.model.User;
import via.pro3.datatierserver.repositories.IUserRepository;
import via.pro3.datatierserver.security.PasswordHasher;

@ExtendWith(MockitoExtension.class)
class AuthGrpcServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private StreamObserver<DataTierProto.LoginResponse> responseObserver;

    @InjectMocks
    private AuthGrpcService authService;

    //successful
    @Test
    void authenticateUser_validCredentials_returnsLoginResponse() {
        DataTierProto.LoginRequest request =
                DataTierProto.LoginRequest.newBuilder()
                        .setUsername("john")
                        .setPassword("password123")
                        .build();

        User user = new User();
        user.setId(1);
        user.setUsername("john");
        user.setRoleId(2);
        user.setPassword(PasswordHasher.hash("password123"));
        user.setIsActive(true);
        user.setPublicKey("PUBLIC_KEY");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        authService.authenticateUser(request, responseObserver);

        ArgumentCaptor<DataTierProto.LoginResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.LoginResponse.class);

        verify(responseObserver).onNext(captor.capture());
        verify(responseObserver).onCompleted();

        DataTierProto.LoginResponse response = captor.getValue();
        assertEquals(1, response.getId());
        assertEquals("john", response.getUsername());
        assertEquals(2, response.getRoleId());
        assertEquals("PUBLIC_KEY", response.getPublicKey());
    }


    //user not found
    @Test
    void authenticateUser_userNotFound_returnsUnauthenticated() {
        DataTierProto.LoginRequest request =
                DataTierProto.LoginRequest.newBuilder()
                        .setUsername("unknown")
                        .setPassword("pw")
                        .build();

        when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        authService.authenticateUser(request, responseObserver);

        verifyUnauthenticated("Invalid username or password");
    }


    //wrong pass
    @Test
    void authenticateUser_wrongPassword_returnsUnauthenticated() {
        DataTierProto.LoginRequest request =
                DataTierProto.LoginRequest.newBuilder()
                        .setUsername("john")
                        .setPassword("wrong")
                        .build();

        User user = new User();
        user.setUsername("john");
        user.setPassword(PasswordHasher.hash("correct"));
        user.setIsActive(true);

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        authService.authenticateUser(request, responseObserver);

        verifyUnauthenticated("Invalid username or password");
    }

    //inactive
    @Test
    void authenticateUser_inactiveUser_returnsPermissionDenied() {
        DataTierProto.LoginRequest request =
                DataTierProto.LoginRequest.newBuilder()
                        .setUsername("john")
                        .setPassword("password")
                        .build();

        User user = new User();
        user.setUsername("john");
        user.setPassword(PasswordHasher.hash("password"));
        user.setIsActive(false);

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        authService.authenticateUser(request, responseObserver);

        verifyGrpcError(Status.PERMISSION_DENIED,
                "Your account has been deactivated. Email felipedafig@gamail to find out why.");
    }

    //no username
    @Test
    void authenticateUser_blankUsername_returnsInvalidArgument() {
        DataTierProto.LoginRequest request =
                DataTierProto.LoginRequest.newBuilder()
                        .setPassword("pw")
                        .build();

        authService.authenticateUser(request, responseObserver);

        verifyGrpcError(Status.INVALID_ARGUMENT, "Username is required");
        verify(userRepository, never()).findByUsername(any());
    }

    //no pass
    @Test
    void authenticateUser_blankPassword_returnsInvalidArgument() {
        DataTierProto.LoginRequest request =
                DataTierProto.LoginRequest.newBuilder()
                        .setUsername("john")
                        .build();

        authService.authenticateUser(request, responseObserver);

        verifyGrpcError(Status.INVALID_ARGUMENT, "Password is required");
        verify(userRepository, never()).findByUsername(any());
    }

    //helper methods
    private void verifyUnauthenticated(String message) {
        verifyGrpcError(Status.UNAUTHENTICATED, message);
    }

    private void verifyGrpcError(Status expectedStatus, String expectedMessage) {
        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(responseObserver).onError(errorCaptor.capture());
        verify(responseObserver, never()).onNext(any());
        verify(responseObserver, never()).onCompleted();

        StatusRuntimeException ex = (StatusRuntimeException) errorCaptor.getValue();
        assertEquals(expectedStatus.getCode(), ex.getStatus().getCode());
        assertEquals(expectedMessage, ex.getStatus().getDescription());
    }
}