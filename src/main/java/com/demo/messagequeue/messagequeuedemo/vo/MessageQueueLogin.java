package com.bithumb.messagequeue.messagequeuedemo.vo;

import lombok.Data;

@Data
public class MessageQueueLogin {
    private String id;
    private String topicName;
    private String phoneNumber;
    private String message;
}
