package com.instahipsta.webappTest.config;


import org.apache.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {
    Logger logger = Logger.getLogger(RabbitConfiguration.class);

    @Value("${spring.rabbitmq.host}")
    private String mqHost;
    @Value("${spring.rabbitmq.username}")
    private String mqUsername;
    @Value("${spring.rabbitmq.password}")
    private String mqPassword;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(mqHost);
        connectionFactory.setUsername(mqUsername);
        connectionFactory.setPassword(mqPassword);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange("mailExchange");
        return rabbitTemplate;
    }

    @Bean
    public Queue mailQueue() {
        return new Queue("mailQueue");
    }

    @Bean
    public Queue errorQueue() {
        return new Queue("errorQueue");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("mailExchange");
    }

    @Bean
    public Binding mailBinding() {
        return BindingBuilder.bind(mailQueue()).to(directExchange()).with("mail");
    }

    @Bean
    public Binding errorBinding() {
        return BindingBuilder.bind(errorQueue()).to(directExchange()).with("error");
    }

}

