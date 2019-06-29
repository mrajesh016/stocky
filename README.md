# Stocky Service
Stock Service on Spring Boot  
All endpoints require Basic Auth credentials: stocky/stocky

## Build
run `mvn clean package` 

## Run Stocky 
run `mvn spring-boot:run`      

## Swagger UI
http://localhost:8080/swagger-ui.html

## Integration & Unit Tests
run `mvn clean verify` to run all unit and integration tests.

## Code Coverage
view unit tests code coverage report at: `target/site/jacoco/index.html`.

Change default port value in application.properties
