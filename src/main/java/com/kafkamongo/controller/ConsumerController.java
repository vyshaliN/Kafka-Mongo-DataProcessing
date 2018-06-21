package com.kafkamongo.controller;

import com.kafkamongo.model.Employee;
import com.kafkamongo.repository.EmployeeRepository;
import com.kafkamongo.service.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/kafkaMongo")
public class ConsumerController {

    @Autowired
    private  KafkaConsumer consumer;

    @Autowired
    EmployeeRepository repository;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Employee employee) {
        repository.save(employee);
    }

    @RequestMapping(value = "/{id}")
    public Employee consumeMessage (@PathVariable String id, Employee employee){

        try{
            Employee employeeFromMongo = repository.findByEmpId(id);
            List<Employee> listGenerated = consumer.processMessage(employee);
            Iterator it = listGenerated.iterator();
            while(it.hasNext()){
                Employee prodFromKafka = (Employee) it.next();
                if(prodFromKafka.getEmpId().equalsIgnoreCase(id)){
                    employeeFromMongo.setAddress(prodFromKafka.getAddress());
                }
            }
            return employeeFromMongo;
        }catch (Exception e){
            System.out.println("Exception"+e);
            return null;
        }
    }
}
