package via.pro3.rabbitmqbidsserver.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import via.pro3.rabbitmqbidsserver.config.RabbitMQBidsConfig;
import via.pro3.rabbitmqbidsserver.model.BidsNotificationMessage;

@Service
public class BidsNotificationPublisher {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(BidsNotificationPublisher.class);

    private static final String EXCHANGE = "bid_notifications_exchange";
    private static final String ROUTING_KEY = "bid.notifications";

    public BidsNotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public boolean sendNotification(BidsNotificationMessage message) {
        try {
            LOGGER.info("Publishing notification message: {}", message.toString());

            rabbitTemplate.convertAndSend(
                    RabbitMQBidsConfig.EXCHANGE,
                    RabbitMQBidsConfig.ROUTING_KEY,
                    message
            );

            return true;
        }
        catch (Exception ex) {
            LOGGER.error("Error sending notification: {}", ex.getMessage());
            return false;
        }
    }
}