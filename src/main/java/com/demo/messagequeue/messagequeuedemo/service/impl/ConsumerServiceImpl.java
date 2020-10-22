package com.bithumb.messagequeue.messagequeuedemo.service.impl;

import com.bithumb.messagequeue.messagequeuedemo.service.ConsumerService;
import com.bithumb.messagequeue.messagequeuedemo.service.ProducerService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Properties;

@Slf4j
@Service
public class ConsumerServiceImpl<T> implements ConsumerService<T> {

    @Autowired
    Gson gson;
    @Autowired
    ProducerService<T> producerService;

    @Value("${kafka.servers}")
    private String kafkaServers;
    @Value("${kafka.topic.loginPushTopicName}")
    private String loginPushTopicName;

    @PostConstruct
    void init(){
        var threadConsumer = new Thread(() -> initConsumer());
        threadConsumer.start();
    }

    public void initConsumer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaServers);
        properties.put("session.timeout.ms", "10000");             // session 설정
        properties.put("group.id", "consumerGroup1");
        properties.put("enable.auto.commit", "false");
        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(loginPushTopicName));

        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(100);//deprecated? 하지만 의미만 변경?
            for (ConsumerRecord<String, String> record : records) {
                try {
                    consume(record);
                } catch (Exception e) {
                    //return to queue that fail record
                    log.error("Produce Again Fail Record Start :" + record.value(), e);
                    try {
                        produceAgainFailRecord(record);
                        log.error("Produce Again Fail Record Success :" + record.value(), e);
                    } catch (Exception e2) {
                        log.error("Fatal Error : Burn Record : " + record.value(), e2);
                    }
                }
            }
            if (records.count() > 0)
                consumer.commitSync();
        }
    }

    @Override
    public void consume(ConsumerRecord<String, String> record) throws Exception{
        log.info("\nQueue key:"+record.key()+"\nQueue Value:"+record.value());
    }

    private void produceAgainFailRecord(ConsumerRecord<String, String> record) throws Exception{
        producerService.send(record.topic(), gson.fromJson(record.value(), new TypeToken<T>(){}.getType()));
    }
}
