package com.kafkamongo.repository;

import com.kafkamongo.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee,String>{

    Employee findByEmpId(String id);
}
