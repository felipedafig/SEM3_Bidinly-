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
    public void createNotification(DataTierProto.CreateNotificationRequest request,
                                  StreamObserver<DataTierProto.NotificationResponse> responseObserver) {
        try {
            if (request.getRecipientType() == null || request.getRecipientType().trim().isEmpty()) {
                responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                        .withDescription("RecipientType is required")
                        .asRuntimeException());
                return;
            }

            Notification newNotification = new Notification();
            newNotification.setBidId(request.getBidId());
            newNotification.setRecipientType(request.getRecipientType());
            newNotification.setPropertyId(request.getPropertyId());
            newNotification.setMessage(request.getMessage());
            newNotification.setIsRead(false);
            newNotification.setCreatedAt(Instant.now());

            if ("Buyer".equals(request.getRecipientType())) {
                if (!request.hasBuyerId()) {
                    responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("BuyerId is required when RecipientType is Buyer")
                            .asRuntimeException());
                    return;
                }
                newNotification.setBuyerId(request.getBuyerId());
                newNotification.setAgentId(null);
            } else if ("Agent".equals(request.getRecipientType())) {
                if (!request.hasAgentId()) {
                    responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("AgentId is required when RecipientType is Agent")
                            .asRuntimeException());
                    return;
                }
                newNotification.setAgentId(request.getAgentId());
                newNotification.setBuyerId(null);
            } else {
                responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                        .withDescription("RecipientType must be 'Buyer' or 'Agent'")
                        .asRuntimeException());
                return;
            }

            if (request.hasStatus() && !request.getStatus().isEmpty()) {
                newNotification.setStatus(request.getStatus());
            }

            if (request.hasPropertyTitle() && !request.getPropertyTitle().isEmpty()) {
                newNotification.setPropertyTitle(request.getPropertyTitle());
            }

            Notification savedNotification = notificationRepository.save(newNotification);

            DataTierProto.NotificationResponse response = convertToNotificationResponse(savedNotification);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Error creating notification: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getNotifications(DataTierProto.GetNotificationsRequest request,
                                 StreamObserver<DataTierProto.GetNotificationsResponse> responseObserver) {
        try {
            List<Notification> notifications;
            String recipientType = request.hasRecipientType() ? request.getRecipientType() : null;

            if ("Buyer".equals(recipientType) && request.hasBuyerId()) {
                if (request.hasIsRead()) {
                    notifications = notificationRepository.findByRecipientTypeAndBuyerIdAndIsRead(
                            "Buyer", request.getBuyerId(), request.getIsRead());
                } else {
                    notifications = notificationRepository.findByRecipientTypeAndBuyerId("Buyer", request.getBuyerId());
                }
            } else if ("Agent".equals(recipientType) && request.hasAgentId()) {
                if (request.hasIsRead()) {
                    notifications = notificationRepository.findByRecipientTypeAndAgentIdAndIsRead(
                            "Agent", request.getAgentId(), request.getIsRead());
                } else {
                    notifications = notificationRepository.findByRecipientTypeAndAgentId("Agent", request.getAgentId());
                }
            } else if (request.hasBuyerId()) {
                // Backward compatibility: if BuyerId is provided but no RecipientType, filter by BuyerId
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
                .setRecipientType(notification.getRecipientType() != null ? notification.getRecipientType() : "")
                .setPropertyId(notification.getPropertyId() != null ? notification.getPropertyId() : 0)
                .setMessage(notification.getMessage() != null ? notification.getMessage() : "")
                .setIsRead(notification.getIsRead() != null ? notification.getIsRead() : false);

        if (notification.getBuyerId() != null) {
            builder.setBuyerId(notification.getBuyerId());
        }

        if (notification.getAgentId() != null) {
            builder.setAgentId(notification.getAgentId());
        }

        if (notification.getStatus() != null && !notification.getStatus().isEmpty()) {
            builder.setStatus(notification.getStatus());
        }

        if (notification.getCreatedAt() != null) {
            builder.setCreatedAtSeconds(notification.getCreatedAt().getEpochSecond());
        }

        if (notification.getPropertyTitle() != null && !notification.getPropertyTitle().isEmpty()) {
            builder.setPropertyTitle(notification.getPropertyTitle());
        }

        return builder.build();
    }
}

