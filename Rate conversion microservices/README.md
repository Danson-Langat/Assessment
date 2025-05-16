API Endpoints:
Rate service:

Status Check: GET/status.
Rate Conversion: GET /rate?from=USD&to=EUR

Main Service:

Currency Conversion: POST /convert
Status Check :  GET/convert/status 

Ports: 

Service			Port
Main Service		8002
Rate Service 		8001
Database		5432

Auth:

Username		Password
admin			Admin@123





command to execute to build and run the services on docker:

cd assessment
./mvnw clean package

cd ../main-service
./mvnw clean package


Command to start all the services:

docker-compose up --build


loom video link on the explanation on how the services run:
https://www.loom.com/share/0a52b6c06b644ffc84c5a51cc933edf2?sid=0bddcde0-4396-4af7-9608-e289bbde1f7a






