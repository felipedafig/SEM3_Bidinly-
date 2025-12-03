package via.pro3.rabbitmqbidsserver.model;

public record BidsNotificationMessage(
        int bidId,
        int buyerId,
        int propertyId,
        String status,
        String message,
        String propertyTitle
) {}