package com.instahipsta.webappTest.messaging.tasks;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageWithActivationKey {

    @Value("${spring.mail.username}")
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String message;

    public String getEmailTo() { return emailTo; }

    public void setEmailTo(String emailTo) { this.emailTo = emailTo; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getEmailFrom() { return emailFrom; }

    public void setEmailFrom(String emailFrom) { this.emailFrom = emailFrom; }

}
