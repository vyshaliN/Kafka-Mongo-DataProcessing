package com.kafkamongo.service;

import com.kafkamongo.model.Employee;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumer {

    List<Employee> employeeDetails =new ArrayList<Employee>();

    @KafkaListener(topics="${kafka.topic}")
    public List<Employee> processMessage(Employee employee) {
        if(employee.getEmpId()!=null)
            employeeDetails.add(employee);
        System.out.println("received content = " + employee);
        return employeeDetails;
    }
}
