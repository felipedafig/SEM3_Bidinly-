package via.pro3.paymentserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.grpc.stub.StreamObserver;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import via.pro3.paymentserver.grpc.PaymentProto;
import via.pro3.paymentserver.model.ValidCard;
import via.pro3.paymentserver.publisher.PaymentPublisher;
import via.pro3.paymentserver.repositories.IValidCardRepository;

@ExtendWith(MockitoExtension.class)
class PaymentGrpcServiceTest {

    @Mock
    private IValidCardRepository validCardRepository;

    @Mock
    private PaymentPublisher paymentPublisher;

    @Mock
    private StreamObserver<PaymentProto.ValidateCardResponse> responseObserver;

    @InjectMocks
    private PaymentGrpcService paymentService;

    // successful
    @Test
    void validateCard_validCard_returnsValid_andPublishesEvent() {
        PaymentProto.ValidateCardRequest request =
                PaymentProto.ValidateCardRequest.newBuilder()
                        .setCardNumber("1234567812345678")
                        .setExpirationDate("12/30")
                        .setCvc("123")
                        .setName("John Doe")
                        .setPropertyId(1)
                        .setBidId(2)
                        .setBuyerId(3)
                        .setAgentId(4)
                        .build();

        ValidCard card = new ValidCard();
        card.setExpirationDate("12/30");
        card.setCvc("123");
        card.setName("John Doe");

        when(validCardRepository.findById("1234567812345678"))
                .thenReturn(Optional.of(card));

        paymentService.validateCard(request, responseObserver);

        ArgumentCaptor<PaymentProto.ValidateCardResponse> captor =
                ArgumentCaptor.forClass(PaymentProto.ValidateCardResponse.class);

        verify(responseObserver).onNext(captor.capture());
        verify(responseObserver).onCompleted();

        assertEquals(true, captor.getValue().getIsValid());
        verify(paymentPublisher).publishPaymentValidated(any());
    }

    //no card found
    @Test
    void validateCard_cardNotFound_returnsInvalid_andDoesNotPublish() {
        PaymentProto.ValidateCardRequest request =
                PaymentProto.ValidateCardRequest.newBuilder()
                        .setCardNumber("9999")
                        .build();

        when(validCardRepository.findById("9999"))
                .thenReturn(Optional.empty());

        paymentService.validateCard(request, responseObserver);

        ArgumentCaptor<PaymentProto.ValidateCardResponse> captor =
                ArgumentCaptor.forClass(PaymentProto.ValidateCardResponse.class);

        verify(responseObserver).onNext(captor.capture());
        verify(responseObserver).onCompleted();

        assertEquals(false, captor.getValue().getIsValid());
        verify(paymentPublisher, never()).publishPaymentValidated(any());
    }
}