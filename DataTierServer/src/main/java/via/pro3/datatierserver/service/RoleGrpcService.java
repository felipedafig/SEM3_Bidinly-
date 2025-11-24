package via.pro3.datatierserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.grpc.RoleServiceGrpc;
import via.pro3.datatierserver.model.Role;
import via.pro3.datatierserver.repositories.IRoleRepository;

import java.util.Optional;

@GrpcService
public class RoleGrpcService extends RoleServiceGrpc.RoleServiceImplBase {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public void getRole(DataTierProto.GetRoleRequest request, StreamObserver<DataTierProto.GetRoleResponse> responseObserver) {
        try {
            Optional<Role> roleOpt = roleRepository.getSingle(request.getRoleId());

            if (roleOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                        .withDescription("Role with id " + request.getRoleId() + " not found")
                        .asRuntimeException());
                return;
            }

            Role role = roleOpt.get();
            DataTierProto.GetRoleResponse response = DataTierProto.GetRoleResponse.newBuilder()
                    .setRoleName(role.getName() != null ? role.getName() : "")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Error retrieving role: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}
