package com.kafkamongo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkamongo.model.Employee;
import com.kafkamongo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository repository;

    @RequestMapping(value = "/{id}")
    public ResponseEntity<String> read(@PathVariable String id) throws JsonProcessingException {
        Employee employee =  repository.findByEmpId(id);
        if(employee == null)
            return new ResponseEntity<>("Employee with "+id+" is not available", HttpStatus.NOT_FOUND);
        else
        {
            String response = new ObjectMapper().writeValueAsString(employee);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrUpdate(@RequestBody Employee employee) throws Exception {
        repository.save(employee);
        return new ResponseEntity<>("Employee details are entered in MongoDB",HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable String id) {
        Employee employee = repository.findByEmpId(id);
        if(employee == null )
            return new ResponseEntity<>("Employee with "+id+" is not available", HttpStatus.NOT_FOUND);
        else{
            repository.deleteEmployeeByEmpId(id);
            return new ResponseEntity<>("Employee details with "+id+" are deleted", HttpStatus.OK);
        }

    }
}
