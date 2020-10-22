package com.bithumb.messagequeue.messagequeuedemo.service;


public interface ProducerService<T>  {
    void  send(String  topicName, T valueObject);
}
