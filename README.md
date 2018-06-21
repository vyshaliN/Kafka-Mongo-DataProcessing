# Kafka-Mongo-DataProcessing

Implemented endpoints 
*To produce messages through kafka topic 
*Read messages from Kafka topic and then match with record from MongoDB by means of EmployeeId and aggregate results

## Pre-Requisites 

* Mongo and Kafka services must be available in the instances where the application is running
* MongoDB should be installed and mapped to the port 27017 while the kafka should be mapped to port 9092
* The database name for MongoDB must be configured in the application.properties
* Ensure Mongo and Kafka instances are in running state while running the application


## Run the application

git clone https://github.com/vyshaliN/Kafka-Mongo-DataProcessing.git

Run : "mvn clean package" and build the jar

Run : java -jar kafka-mongo-0.0.1-SNAPSHOT.jar

The endpoints will now be accessible via 8080 

### Insert/Update into MongoDB

Method : POST
Endpoint : http://localhost:8080/employee
RequestBody : 
{
	"empId" : "E003",
	"empName" : "Ramalakshmi",
	"designation" : "Tech Lead"
}
Response Status : 200

### Read from MongoDB

Method : GET
Endpoint : http://localhost:8080/employee/E003
ResponseBody : 
{
	"empId" : "E003",
	"empName" : "Ramalakshmi",
	"designation" : "Tech Lead"
}
Response Status : 200

### Delete from MongoDB

Method : DELETE
Endpoint : http://localhost:8080/employee/E003
Response Status : 200

### Produce messages

Method : POST
Endpoint : http://localhost:8080/messages
RequestBody : 
{
	"empId" : "E003",
	"empName" : "Ramalakshmi",
	"address" : "Madurai,TN"	
}

### Receive messages and aggregate with MongoDB

Method : GET
Endpoint : http://localhost:8080/kakfaMongo/E003
ResponseBody:
{
	"empId" : "E003",
	"empName" : "Ramalakshmi",
	"designation" : "Tech Lead"
	"address" : "Madurai,TN"	
}






 