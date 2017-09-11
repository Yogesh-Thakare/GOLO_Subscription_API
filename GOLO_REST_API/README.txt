			* Paysafe Coding Challenge - Yogesh Thakare *

This is Spring Boot based application
Application can be deployed and viewed on Local: (http://localhost:8090/)

## Supported features

 - Simple MVC based architecture; view index.html available for demo purpose
 
 - Exception handling for illegal request and runtime exceptions
 
 - Implemented following notification APIs:
 
 -> Monitoring Server
 - start: http://[host]:[port]/api/monitor/start?hostname=[server]&interval=[time]
 - Example: http://localhost:8090/api/monitor/start?hostname=https://api.test.paysafe.com/accountmanagement/monitor&interval=500
     
 - stop: http://[host]:[port]/api/monitor/stop?hostname=[server]
 - Example:http://localhost:8090/api/monitor/stop?hostname=https://api.test.paysafe.com/accountmanagement/monitor
   
 -> Get Server Statistics
 - overview: http://[host]:[port]/api/monitor/overview?hostname=[server]
 - Example:http://localhost:8090/api/monitor/overview?hostname=https://api.test.paysafe.com/accountmanagement/monitor
  
## Technology stack

  - Spring Boot
  - REST API
  - Mockito/JUnit tests
  - JavaDoc
  
## Run 

Locally:
-Run mvn clean 
-Run mvn install
-Run main class Application, so that application will be up and above REST calls are accessible.

Note* please see attached GOLO API Documentation for response obtained in Postman client