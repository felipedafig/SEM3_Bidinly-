package via.pro3.rabbitmqbidsserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import via.pro3.rabbitmqbidsserver.model.BidsNotificationMessage;
import via.pro3.rabbitmqbidsserver.publisher.BidsNotificationPublisher;


//POST http://localhost:8080/api/v1/notify
@RestController
@RequestMapping("/api/v1")
public class BidsNotificationController {

    private final BidsNotificationPublisher publisher;

    public BidsNotificationController(BidsNotificationPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/notify")
    public ResponseEntity<String> sendNotification(@RequestBody BidsNotificationMessage message) {

        if (publisher.sendNotification(message)) {
            System.out.println("Notification sent to RabbitMQ: " + message.status());
            return ResponseEntity.ok("Notification sent to RabbitMQ");
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send notification");
        }
    }
}