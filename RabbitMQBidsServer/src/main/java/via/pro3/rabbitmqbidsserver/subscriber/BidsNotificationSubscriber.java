package via.pro3.rabbitmqbidsserver.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import via.pro3.rabbitmqbidsserver.model.BidsNotificationMessage;

@Component
public class BidsNotificationSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidsNotificationSubscriber.class);

    @RabbitListener(queues = "bid_notifications_queue", id = "bidNotificationListener")
    public void receiveNotification(BidsNotificationMessage message) {

        LOGGER.info("Received notification: {}", message.toString());

        System.out.println("\nNOTIFICATION RECEIVED");
        System.out.println("Bid ID:        " + message.bidId());
        System.out.println("Buyer ID:      " + message.buyerId());
        System.out.println("Property ID:   " + message.propertyId());
        System.out.println("Status:        " + message.status());
        System.out.println("Message:       " + message.message());
        System.out.println("Property Title:" + message.propertyTitle());
    }
}