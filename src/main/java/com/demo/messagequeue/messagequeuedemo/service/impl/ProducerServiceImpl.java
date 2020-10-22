package com.bithumb.messagequeue.messagequeuedemo.service.impl;

import com.bithumb.messagequeue.messagequeuedemo.service.ProducerService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Service
public class ProducerServiceImpl<T> implements ProducerService<T> {

    @Autowired
    Gson gson;

    @Value("${kafka.servers}")
    private String kafkaServers;

    private KafkaProducer<String, String> producer;

    @PostConstruct
    public void initProducer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaServers);
        properties.put("group.id", "consumerGroup1");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer(properties);
    }

    @Override
    public void send(String topicName, Object valueObject) {
        String uuid = UUID.randomUUID().toString();
        try{
            producer.send(new ProducerRecord(topicName, uuid, gson.toJson(valueObject) ));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            producer.flush();
            //producer.close();
        }
    }
}
