package via.pro3.datatierserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.model.Notification;
import via.pro3.datatierserver.repositories.INotificationRepository;

@ExtendWith(MockitoExtension.class)
class NotificationGrpcServiceTest {

    @Mock
    private INotificationRepository notificationRepository;

    @Mock
    private StreamObserver<DataTierProto.NotificationResponse> notificationObserver;

    @InjectMocks
    private NotificationGrpcService notificationService;

    //create notif
    @Test
    void createNotification_missingUserId_returnsInvalidArgument() {
        DataTierProto.CreateNotificationRequest request =
                DataTierProto.CreateNotificationRequest.newBuilder()
                        .setBidId(1)
                        .setPropertyId(2)
                        .setMessage("Test message")
                        .build();

        notificationService.createNotification(request, notificationObserver);

        verifyGrpcError(
                notificationObserver,
                Status.INVALID_ARGUMENT,
                "UserId is required for notification"
        );

        verify(notificationRepository, never()).save(any());
    }

    @Test
    void createNotification_validRequest_savesAndReturnsResponse() {
        Notification saved = new Notification();
        saved.setId(1);
        saved.setUserId(10);
        saved.setBidId(2);
        saved.setPropertyId(3);
        saved.setMessage("Hello");
        saved.setIsRead(false);
        saved.setCreatedAt(Instant.now());

        when(notificationRepository.save(any(Notification.class)))
                .thenReturn(saved);

        DataTierProto.CreateNotificationRequest request =
                DataTierProto.CreateNotificationRequest.newBuilder()
                        .setUserId(10)
                        .setBidId(2)
                        .setPropertyId(3)
                        .setMessage("Hello")
                        .build();

        notificationService.createNotification(request, notificationObserver);

        ArgumentCaptor<DataTierProto.NotificationResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.NotificationResponse.class);

        verify(notificationObserver).onNext(captor.capture());
        verify(notificationObserver).onCompleted();

        assertEquals(1, captor.getValue().getId());
        assertEquals(10, captor.getValue().getUserId());
        assertEquals(false, captor.getValue().getIsRead());
    }

    //get notif
    @Test
    void getNotification_notFound_returnsNotFound() {
        when(notificationRepository.findById(99))
                .thenReturn(Optional.empty());

        DataTierProto.GetNotificationRequest request =
                DataTierProto.GetNotificationRequest.newBuilder()
                        .setId(99)
                        .build();

        notificationService.getNotification(request, notificationObserver);

        verifyGrpcError(
                notificationObserver,
                Status.NOT_FOUND,
                "Notification with id 99 not found"
        );
    }

    @Test
    void getNotification_found_returnsResponse() {
        Notification notification = new Notification();
        notification.setId(5);
        notification.setUserId(1);
        notification.setMessage("Test");

        when(notificationRepository.findById(5))
                .thenReturn(Optional.of(notification));

        DataTierProto.GetNotificationRequest request =
                DataTierProto.GetNotificationRequest.newBuilder()
                        .setId(5)
                        .build();

        notificationService.getNotification(request, notificationObserver);

        ArgumentCaptor<DataTierProto.NotificationResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.NotificationResponse.class);

        verify(notificationObserver).onNext(captor.capture());
        verify(notificationObserver).onCompleted();

        assertEquals(5, captor.getValue().getId());
        assertEquals("Test", captor.getValue().getMessage());
    }

    //mark as read
    @Test
    void markNotificationAsRead_notFound_returnsNotFound() {
        when(notificationRepository.findById(7))
                .thenReturn(Optional.empty());

        DataTierProto.MarkNotificationAsReadRequest request =
                DataTierProto.MarkNotificationAsReadRequest.newBuilder()
                        .setId(7)
                        .build();

        notificationService.markNotificationAsRead(request, notificationObserver);

        verifyGrpcError(
                notificationObserver,
                Status.NOT_FOUND,
                "Notification with id 7 not found"
        );
    }

    @Test
    void markNotificationAsRead_found_marksReadAndReturnsResponse() {
        Notification notification = new Notification();
        notification.setId(7);
        notification.setIsRead(false);

        Notification updated = new Notification();
        updated.setId(7);
        updated.setIsRead(true);

        when(notificationRepository.findById(7))
                .thenReturn(Optional.of(notification));
        when(notificationRepository.save(any(Notification.class)))
                .thenReturn(updated);

        DataTierProto.MarkNotificationAsReadRequest request =
                DataTierProto.MarkNotificationAsReadRequest.newBuilder()
                        .setId(7)
                        .build();

        notificationService.markNotificationAsRead(request, notificationObserver);

        ArgumentCaptor<DataTierProto.NotificationResponse> captor =
                ArgumentCaptor.forClass(DataTierProto.NotificationResponse.class);

        verify(notificationObserver).onNext(captor.capture());
        verify(notificationObserver).onCompleted();

        assertEquals(true, captor.getValue().getIsRead());
    }

    //helper methods
    private void verifyGrpcError(
            StreamObserver<?> observer,
            Status expectedStatus,
            String expectedMessage
    ) {
        ArgumentCaptor<Throwable> captor = ArgumentCaptor.forClass(Throwable.class);
        verify(observer).onError(captor.capture());

        StatusRuntimeException ex = (StatusRuntimeException) captor.getValue();
        assertEquals(expectedStatus.getCode(), ex.getStatus().getCode());
        assertEquals(expectedMessage, ex.getStatus().getDescription());
    }
}