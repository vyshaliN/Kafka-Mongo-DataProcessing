package com.kafkamongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootKafkaMongoApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootKafkaMongoApplication.class, args);
    }

    /*@Autowired
    KafkaProducer producer;

    @Override
    public void run(String... arg0) throws Exception {
        Employee p = new Employee();
        producer.send(p);

    }*/
}
