package com.instahipsta.webappTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.instahipsta.webappTest.config.RabbitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;


@Import(RabbitConfiguration.class)
@SpringBootApplication
public class Application {

    @Autowired
    private ObjectMapper myObjectMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void setUp() {
        myObjectMapper.registerModule(new JavaTimeModule());
    }

}