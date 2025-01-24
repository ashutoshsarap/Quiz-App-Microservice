# Quiz-App-Microservice
As the name suggests this is a microservice project
The user can CREATE,ADD,DELETE a quiz as per the requirements
Initially a monolithic structure of this app was designed which was later converted into multiple microservices
The user interacts with the API-gateway to get responses for its requests
Microservices - 1.Question-service 2. Quiz-service are registered on the Eureka server
The API-gateway interacts with these 2 microservices as needed and gets the response
Both the microservices have different databases
The 2 microservices can communicate with each other via open feign client service
