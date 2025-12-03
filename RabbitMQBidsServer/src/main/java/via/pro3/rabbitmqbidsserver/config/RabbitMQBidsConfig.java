package via.pro3.rabbitmqbidsserver.config;

import org.springframework.amqp.core.*;
        import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQBidsConfig {

    public static final String EXCHANGE = "bid_notifications_exchange";
    public static final String QUEUE = "bid_notifications_queue";
    public static final String ROUTING_KEY = "bid.notifications";

    // exchange
    @Bean
    public Exchange bidExchange() {return new DirectExchange(EXCHANGE);}

    // queue
    @Bean
    public Queue bidQueue() {return new Queue(QUEUE, true);}

    // binding queue to exchange with routing key
    @Bean
    public Binding bidBinding() {
        return BindingBuilder
                .bind(bidQueue())
                .to(bidExchange())
                .with(ROUTING_KEY)
                .noargs();}

    // JSON converter
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate (used for sending messages)
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;}
}