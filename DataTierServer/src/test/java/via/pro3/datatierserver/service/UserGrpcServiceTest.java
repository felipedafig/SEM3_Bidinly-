package via.pro3.datatierserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
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

@ExtendWith(MockitoExtension.class)
class UserGrpcServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private StreamObserver<DataTierProto.UserResponse> userResponseObserver;

    @Mock
    private StreamObserver<DataTierProto.DeleteUserResponse> deleteResponseObserver;

    @InjectMocks
    private UserGrpcService userService;

    @Test
    void createUser_missingUsername_returnsInvalidArgument() {
        DataTierProto.CreateUserRequest request =
                DataTierProto.CreateUserRequest.newBuilder()
                        .setPassword("secret")
                        .setRoleId(1)
                        .build();

        userService.createUser(request, userResponseObserver);

        verifyGrpcError(
                userResponseObserver,
                Status.INVALID_ARGUMENT,
                "Username is required"
        );
    }

    @Test
    void createUser_missingPassword_returnsInvalidArgument() {
        DataTierProto.CreateUserRequest request =
                DataTierProto.CreateUserRequest.newBuilder()
                        .setUsername("bob")
                        .setRoleId(1)
                        .build();

        userService.createUser(request, userResponseObserver);

        verifyGrpcError(
                userResponseObserver,
                Status.INVALID_ARGUMENT,
                "Password is required"
        );
    }

    @Test
    void createUser_usernameExists_returnsAlreadyExists() {
        when(userRepository.findByUsername("bob"))
                .thenReturn(Optional.of(new User()));

        DataTierProto.CreateUserRequest request =
                DataTierProto.CreateUserRequest.newBuilder()
                        .setUsername("bob")
                        .setPassword("secret")
                        .setRoleId(1)
                        .build();

        userService.createUser(request, userResponseObserver);

        verifyGrpcError(
                userResponseObserver,
                Status.ALREADY_EXISTS,
                "Username already exists: bob"
        );
    }

    @Test
    void createUser_validRequest_savesAndReturnsResponse() {
        when(userRepository.findByUsername("alice"))
                .thenReturn(Optional.empty());

        User saved = new User();
        saved.setId(1);
        saved.setUsername("alice");
        saved.setRoleId(2);
        saved.setIsActive(true);

        when(userRepository.save(any(User.class)))
                .thenReturn(saved);

        DataTierProto.CreateUserRequest request =
                DataTierProto.CreateUserRequest.newBuilder()
                        .setUsername("alice")
                        .setPassword("secret")
                        .setRoleId(2)
                        .build();

        userService.createUser(request, userResponseObserver);

        ArgumentCaptor<DataTierProto.UserResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.UserResponse.class);

        verify(userResponseObserver).onNext(captor.capture());
        verify(userResponseObserver).onCompleted();

        assertEquals("alice", captor.getValue().getUsername());
        assertEquals(2, captor.getValue().getRoleId());
    }

    @Test
    void getUser_notFound_returnsNotFound() {
        when(userRepository.getSingle(99))
                .thenReturn(Optional.empty());

        DataTierProto.GetUserRequest request =
                DataTierProto.GetUserRequest.newBuilder()
                        .setId(99)
                        .build();

        userService.getUser(request, userResponseObserver);

        verifyGrpcError(
                userResponseObserver,
                Status.NOT_FOUND,
                "User with id 99 not found"
        );
    }

    @Test
    void getUser_found_returnsResponse() {
        User user = new User();
        user.setId(1);
        user.setUsername("john");
        user.setRoleId(3);

        when(userRepository.getSingle(1))
                .thenReturn(Optional.of(user));

        DataTierProto.GetUserRequest request =
                DataTierProto.GetUserRequest.newBuilder()
                        .setId(1)
                        .build();

        userService.getUser(request, userResponseObserver);

        ArgumentCaptor<DataTierProto.UserResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.UserResponse.class);

        verify(userResponseObserver).onNext(captor.capture());
        verify(userResponseObserver).onCompleted();

        assertEquals("john", captor.getValue().getUsername());
    }

    @Test
    void updateUser_notFound_returnsNotFound() {
        when(userRepository.getSingle(5))
                .thenReturn(Optional.empty());

        DataTierProto.UpdateUserRequest request =
                DataTierProto.UpdateUserRequest.newBuilder()
                        .setId(5)
                        .build();

        userService.updateUser(request, userResponseObserver);

        verifyGrpcError(
                userResponseObserver,
                Status.NOT_FOUND,
                "User with id 5 not found"
        );
    }

    @Test
    void updateUser_usernameExists_returnsAlreadyExists() {
        User existing = new User();
        existing.setId(1);

        User other = new User();
        other.setId(2);

        when(userRepository.getSingle(1))
                .thenReturn(Optional.of(existing));
        when(userRepository.findByUsername("taken"))
                .thenReturn(Optional.of(other));

        DataTierProto.UpdateUserRequest request =
                DataTierProto.UpdateUserRequest.newBuilder()
                        .setId(1)
                        .setUsername("taken")
                        .build();

        userService.updateUser(request, userResponseObserver);

        verifyGrpcError(
                userResponseObserver,
                Status.ALREADY_EXISTS,
                "Username already exists: taken"
        );
    }

    @Test
    void deleteUser_notFound_returnsNotFound() {
        when(userRepository.getSingle(7))
                .thenReturn(Optional.empty());

        DataTierProto.DeleteUserRequest request =
                DataTierProto.DeleteUserRequest.newBuilder()
                        .setId(7)
                        .build();

        userService.deleteUser(request, deleteResponseObserver);

        verifyGrpcError(
                deleteResponseObserver,
                Status.NOT_FOUND,
                "User with id 7 not found"
        );
    }

    @Test
    void deleteUser_found_deletesAndReturnsSuccess() {
        User user = new User();
        user.setId(7);

        when(userRepository.getSingle(7))
                .thenReturn(Optional.of(user));

        DataTierProto.DeleteUserRequest request =
                DataTierProto.DeleteUserRequest.newBuilder()
                        .setId(7)
                        .build();

        userService.deleteUser(request, deleteResponseObserver);

        ArgumentCaptor<DataTierProto.DeleteUserResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.DeleteUserResponse.class);

        verify(userRepository).deleteById(7);
        verify(deleteResponseObserver).onNext(captor.capture());
        verify(deleteResponseObserver).onCompleted();

        assertEquals(true, captor.getValue().getSuccess());
    }

    //helpers
    private void verifyGrpcError(
            StreamObserver<?> observer,
            Status expectedStatus,
            String expectedMessage
    ) {
        ArgumentCaptor<Throwable> captor =
                ArgumentCaptor.forClass(Throwable.class);

        verify(observer).onError(captor.capture());

        StatusRuntimeException ex =
                (StatusRuntimeException) captor.getValue();

        assertEquals(expectedStatus.getCode(), ex.getStatus().getCode());
        assertEquals(expectedMessage, ex.getStatus().getDescription());
    }
}