package com.kafkamongo.controller;

import com.kafkamongo.model.Employee;
import com.kafkamongo.service.KafkaProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class ProducerController {

    private final KafkaProducer producer;

    public ProducerController(KafkaProducer producer){
        this.producer=producer;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void kafkaSendMsgs (@RequestBody Employee employee){
        producer.send(employee);
    }

}
