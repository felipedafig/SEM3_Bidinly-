package via.pro3.datatierserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.grpc.UserServiceGrpc;
import via.pro3.datatierserver.model.User;
import via.pro3.datatierserver.repositories.IUserRepository;

import java.util.Optional;

@GrpcService
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
            String hashed = passwordEncoder.encode(request.getPassword());
            newUser.setPassword(hashed);
            newUser.setRoleId(request.getRoleId());

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
}
