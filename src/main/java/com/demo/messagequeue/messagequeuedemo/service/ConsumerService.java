package com.bithumb.messagequeue.messagequeuedemo.service;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public interface ConsumerService<T>  {
    void consume(ConsumerRecord<String, String> record) throws Exception;
}
