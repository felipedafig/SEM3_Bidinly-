package via.pro3.datatierserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import via.pro3.datatierserver.grpc.AuthServiceGrpc;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.model.User;
import via.pro3.datatierserver.repositories.IUserRepository;

import java.util.Optional;

@GrpcService
public class AuthGrpcService extends AuthServiceGrpc.AuthServiceImplBase {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void authenticateUser(DataTierProto.LoginRequest request, StreamObserver<DataTierProto.LoginResponse> responseObserver) {
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

            Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
            
            if (userOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.UNAUTHENTICATED
                        .withDescription("Invalid username or password")
                        .asRuntimeException());
                return;
            }
            
            User user = userOpt.get();
            
            if (!user.getPassword().equals(request.getPassword())) {
                responseObserver.onError(io.grpc.Status.UNAUTHENTICATED
                        .withDescription("Invalid username or password")
                        .asRuntimeException());
                return;
            }
            
            if (user.getIsActive() == null || !user.getIsActive()) {
                responseObserver.onError(io.grpc.Status.PERMISSION_DENIED
                        .withDescription("Your account has been deactivated. Email felipedafig@gamail to find out why.")
                        .asRuntimeException());
                return;
            }
            
            DataTierProto.LoginResponse response = DataTierProto.LoginResponse.newBuilder()
                    .setId(user.getId() != null ? user.getId() : 0)
                    .setUsername(user.getUsername() != null ? user.getUsername() : "")
                    .setRoleId(user.getRoleId() != null ? user.getRoleId() : 0)
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (io.grpc.StatusRuntimeException e) {
            responseObserver.onError(e);
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Error authenticating user: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}

