package com.instahipsta.webappTest.messaging.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.webappTest.impl.MailSender;
import com.instahipsta.webappTest.messaging.tasks.MessageWithActivationKey;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMqListener {
    Logger logger = Logger.getLogger(RabbitMqListener.class);

    @Autowired
    private MessageWithActivationKey messageWithActivationKey;

    @Autowired
    private ObjectMapper myObjectMapper;

    @Autowired
    MailSender mailSender;

    @RabbitListener(queues = "mailQueue")
    public void mailWorker(String message) {

        try {
            messageWithActivationKey = myObjectMapper.readValue(message, MessageWithActivationKey.class);
            mailSender.send(messageWithActivationKey.getEmailFrom(),
                    messageWithActivationKey.getEmailTo(),
                    messageWithActivationKey.getSubject(),
                    messageWithActivationKey.getMessage());
        }
        catch (IOException e) { e.printStackTrace(); }

    }

    @RabbitListener(queues = "errorQueue")
    public void errorWorker(String message) {
        System.out.println(message);
    }

}
