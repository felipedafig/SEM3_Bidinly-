package via.pro3.paymentserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import via.pro3.paymentserver.grpc.PaymentServiceGrpc;
import via.pro3.paymentserver.grpc.PaymentProto;
import via.pro3.paymentserver.model.ValidCard;
import via.pro3.paymentserver.repositories.IValidCardRepository;

import java.util.Optional;

@GrpcService
public class PaymentGrpcService extends PaymentServiceGrpc.PaymentServiceImplBase {

    @Autowired
    private IValidCardRepository validCardRepository;

    @Override
    public void validateCard(PaymentProto.ValidateCardRequest request, StreamObserver<PaymentProto.ValidateCardResponse> responseObserver) {
        try {
            // Validate input parameters
            if (request.getCardNumber() == null || request.getCardNumber().trim().isEmpty()) {
                PaymentProto.ValidateCardResponse response = PaymentProto.ValidateCardResponse.newBuilder()
                        .setIsValid(false)
                        .setMessage("Card number is required")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }

            if (request.getExpirationDate() == null || request.getExpirationDate().trim().isEmpty()) {
                PaymentProto.ValidateCardResponse response = PaymentProto.ValidateCardResponse.newBuilder()
                        .setIsValid(false)
                        .setMessage("Expiration date is required")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }

            if (request.getCvc() == null || request.getCvc().trim().isEmpty()) {
                PaymentProto.ValidateCardResponse response = PaymentProto.ValidateCardResponse.newBuilder()
                        .setIsValid(false)
                        .setMessage("CVC is required")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }

            if (request.getName() == null || request.getName().trim().isEmpty()) {
                PaymentProto.ValidateCardResponse response = PaymentProto.ValidateCardResponse.newBuilder()
                        .setIsValid(false)
                        .setMessage("Name is required")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }

            // Find card by card number
            Optional<ValidCard> cardOpt = validCardRepository.findByCardNumber(request.getCardNumber().trim());
            
            if (cardOpt.isEmpty()) {
                PaymentProto.ValidateCardResponse response = PaymentProto.ValidateCardResponse.newBuilder()
                        .setIsValid(false)
                        .setMessage("Card not found")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            
            ValidCard card = cardOpt.get();
            
            // Validate expiration date
            if (!card.getExpirationDate().equals(request.getExpirationDate().trim())) {
                PaymentProto.ValidateCardResponse response = PaymentProto.ValidateCardResponse.newBuilder()
                        .setIsValid(false)
                        .setMessage("Invalid expiration date")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            
            // Validate CVC
            if (!card.getCvc().equals(request.getCvc().trim())) {
                PaymentProto.ValidateCardResponse response = PaymentProto.ValidateCardResponse.newBuilder()
                        .setIsValid(false)
                        .setMessage("Invalid CVC")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            
            // Validate name (case-insensitive comparison)
            if (!card.getName().trim().equalsIgnoreCase(request.getName().trim())) {
                PaymentProto.ValidateCardResponse response = PaymentProto.ValidateCardResponse.newBuilder()
                        .setIsValid(false)
                        .setMessage("Invalid cardholder name")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            
            // All validations passed
            PaymentProto.ValidateCardResponse response = PaymentProto.ValidateCardResponse.newBuilder()
                    .setIsValid(true)
                    .setMessage("Card validated successfully")
                    .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            PaymentProto.ValidateCardResponse response = PaymentProto.ValidateCardResponse.newBuilder()
                    .setIsValid(false)
                    .setMessage("Error validating card: " + e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}

