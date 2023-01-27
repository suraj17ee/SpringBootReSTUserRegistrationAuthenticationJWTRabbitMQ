package com.stackroute.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public Queue queueA() {
        return new Queue("queueA",false);
    }

    @Bean
    public TopicExchange directExchange()
    {
        return new TopicExchange("MyExchange");
    }

    @Bean
    public Binding bindingA(Queue queueA, TopicExchange directExchange)
    {
        return BindingBuilder.bind(queueA).to(directExchange).with("routingA");
    }

    @Bean
    MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
