package via.pro3.paymentserver.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;


public class RabbitMQConfig {

    public static final String payment_validated_exchange = "payment.validated";

    @Bean
    public FanoutExchange paymentValidatedExchange(){ return  new FanoutExchange(payment_validated_exchange);}

    @Bean
    public MessageConverter jsonMessageConverter(){ return new Jackson2JsonMessageConverter();}
}
