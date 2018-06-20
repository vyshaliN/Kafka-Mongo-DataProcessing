package com.kafkamongo.service;

import com.kafkamongo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, Employee> kafkaTemplate;

    @Value("${kafka.topic}")
    String kafkaTopic = "product";

    public void send(Employee employee) {
        System.out.println("sending data=" + employee);
        kafkaTemplate.send(kafkaTopic, employee);
    }
}
