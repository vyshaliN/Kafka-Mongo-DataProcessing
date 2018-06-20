package com.kafkamongo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
public class Employee {

    @Id
    String empId;
    String empName;
    String designation;

    @JsonInclude
    String address;

     /*public Employee(){

    }

    public Employee(String productId,String productName,String description,Double price){
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }*/

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString(){
        String info = String.format("{'empId':%s,'empName':%s,'designation':%s 'address': %s}", empId,empName,designation,address);
        return info;
    }

}
