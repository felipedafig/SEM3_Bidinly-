package via.pro3.paymentserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import via.pro3.paymentserver.grpc.PaymentServiceGrpc;
import via.pro3.paymentserver.grpc.PaymentProto;
import via.pro3.paymentserver.repositories.IValidCardRepository;

@GrpcService
public class PaymentGrpcService extends PaymentServiceGrpc.PaymentServiceImplBase {

    @Autowired
    private IValidCardRepository validCardRepository;

    @Override
    public void validateCard(PaymentProto.ValidateCardRequest request, StreamObserver<PaymentProto.ValidateCardResponse> responseObserver) {
        // TODO: Implement card validation logic
        // For now, just return a placeholder response
        
        PaymentProto.ValidateCardResponse response = PaymentProto.ValidateCardResponse.newBuilder()
                .setIsValid(false)
                .setMessage("Card validation not implemented yet")
                .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

