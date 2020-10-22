package com.bithumb.messagequeue.messagequeuedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@EnableAsync
@EnableScheduling
public class MessagequeuedemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessagequeuedemoApplication.class, args);
    }

}
