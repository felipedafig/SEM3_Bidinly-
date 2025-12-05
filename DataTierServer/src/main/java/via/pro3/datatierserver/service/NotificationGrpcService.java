package via.pro3.datatierserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import via.pro3.datatierserver.grpc.DataTierProto;
import via.pro3.datatierserver.grpc.NotificationServiceGrpc;
import via.pro3.datatierserver.model.Notification;
import via.pro3.datatierserver.repositories.INotificationRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@GrpcService
public class NotificationGrpcService extends NotificationServiceGrpc.NotificationServiceImplBase {

    @Autowired
    private INotificationRepository notificationRepository;

    @Override
    public void getNotifications(DataTierProto.GetNotificationsRequest request,
                                 StreamObserver<DataTierProto.GetNotificationsResponse> responseObserver) {
        try {
            List<Notification> notifications;

            if (request.hasBuyerId()) {
                if (request.hasIsRead()) {
                    notifications = notificationRepository.findByBuyerIdAndIsRead(
                            request.getBuyerId(), request.getIsRead());
                } else {
                    notifications = notificationRepository.findByBuyerId(request.getBuyerId());
                }
            } else {
                notifications = notificationRepository.getMany();
            }

            List<DataTierProto.NotificationResponse> notificationResponses = notifications.stream()
                    .map(this::convertToNotificationResponse)
                    .collect(Collectors.toList());

            DataTierProto.GetNotificationsResponse response = DataTierProto.GetNotificationsResponse.newBuilder()
                    .addAllNotifications(notificationResponses)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Error retrieving notifications: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getNotification(DataTierProto.GetNotificationRequest request,
                                StreamObserver<DataTierProto.NotificationResponse> responseObserver) {
        try {
            Optional<Notification> notificationOpt = notificationRepository.getSingle(request.getId());

            if (notificationOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                        .withDescription("Notification with id " + request.getId() + " not found")
                        .asRuntimeException());
                return;
            }

            Notification notification = notificationOpt.get();
            DataTierProto.NotificationResponse response = convertToNotificationResponse(notification);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Error retrieving notification: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void markNotificationAsRead(DataTierProto.MarkNotificationAsReadRequest request,
                                       StreamObserver<DataTierProto.NotificationResponse> responseObserver) {
        try {
            Optional<Notification> notificationOpt = notificationRepository.getSingle(request.getId());

            if (notificationOpt.isEmpty()) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                        .withDescription("Notification with id " + request.getId() + " not found")
                        .asRuntimeException());
                return;
            }

            Notification notification = notificationOpt.get();
            notification.setIsRead(true);
            Notification updatedNotification = notificationRepository.save(notification);

            DataTierProto.NotificationResponse response = convertToNotificationResponse(updatedNotification);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Error marking notification as read: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    private DataTierProto.NotificationResponse convertToNotificationResponse(Notification notification) {
        DataTierProto.NotificationResponse.Builder builder = DataTierProto.NotificationResponse.newBuilder()
                .setId(notification.getId() != null ? notification.getId() : 0)
                .setBidId(notification.getBidId() != null ? notification.getBidId() : 0)
                .setBuyerId(notification.getBuyerId() != null ? notification.getBuyerId() : 0)
                .setPropertyId(notification.getPropertyId() != null ? notification.getPropertyId() : 0)
                .setStatus(notification.getStatus() != null ? notification.getStatus() : "")
                .setMessage(notification.getMessage() != null ? notification.getMessage() : "")
                .setIsRead(notification.getIsRead() != null ? notification.getIsRead() : false);

        if (notification.getCreatedAt() != null) {
            builder.setCreatedAtSeconds(notification.getCreatedAt().getEpochSecond());
        }

        if (notification.getPropertyTitle() != null && !notification.getPropertyTitle().isEmpty()) {
            builder.setPropertyTitle(notification.getPropertyTitle());
        }

        return builder.build();
    }
}

