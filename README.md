# Stocky Service [![Version](https://img.shields.io/badge/version-1.0.0-green.svg)](https://github.com/mrajesh016/stocky)
> API First: Swagger Top-Down approach

*Stocky* is a RESTful API on Spring Boot built with a top-down approach (First API specs and then code generation.)  

Started with the API contract using OpenAPI specification(OAS) [stocky-service.yml](/src/main/resources/swagger/stocky-service.yml), and then *swagger-codegen* has been used to generate the client code stubs.

## Getting started

### Installation

  Cloning the repository.
  ```bash
  git clone https://github.com/mrajesh016/stocky.git
  ```
### Client stubs
  
  ```bash
  mvn clean generate-resources
  ```
### Building with maven

  ```bash
  mvn clean install
  ```  
### Running locally
  
  ```bash
  mvn spring-boot:run
  ```
### Building docker image
  > make build: docker build -f Dockerfile.yml -t stocky .
  
  ```bash
  make build
  ```
### Running inside container
  > make run: docker run -p 8080:8080 stocky
  
  ```bash
  make run
  ```
  
### Swagger UI  
> visualize and interact with the API’s resources 

   ```bash
   http://localhost:8080/swagger-ui.html
   ```

## Features
> APIs for create, read, update & delete operations on stock Entities.

### API Endpoints

#### *POST*
*/api/stocks* : To Create a stock.

#### *PUT*
*/api/stocks/{stockId}* : To Update a existing stock identified by stockId.

#### *GET*
*/api/stocks/{stockId}* : To get details of a stock identified by stockId.\
*/api/stocks/* : To get paginated list of stocks.

### *DELETE*
*/api/stocks/{stockId}* : To delete a stock identified by stockId.

Refer: [stocky-service.yml](/src/main/resources/swagger/stocky-service.yml) for more details.

### Tests

#### **Integration Tests**: 
Tests which cover the whole path through the application. 
In these tests, we send a request to the application and check that it 
responds correctly and has changed the database state according to our expectations.

#### **Unit Tests**
Test for individual layers (Controller & Service Layer) have been written with JUnit to validate that 
each layer performs as designed individually.

### Run Integration & Unit Tests
   
   ```bash
   mvn clean verify
   ```

## Code Coverage [![Code coverage](https://img.shields.io/badge/code%20coverage-97%25-brightgreen.svg)](https://htmlpreview.github.io/?https://github.com/mrajesh016/stocky/blob/master/jacoco.html)

view unit tests code coverage report at: `target/site/jacoco/index.html`.

## API usage (Sample Curls)
**POST /api/stocks/**
```
Request:

curl -X POST \
http://localhost:8080/api/stocks/ \
-H 'Authorization: Basic c3RvY2t5OnN0b2NreQ==' \
-H 'Content-Type: application/json' \
-H 'cache-control: no-cache' \
-d '{
"currentPrice": 16.6,
"stockName": "payconiqStock"
}'
  
Response:

{
  "stockId": 1,
  "stockName": "payconiqStock",
  "currentPrice": 16.6,
  "created": "2019-07-03T16:28:20.526+05:30",
  "lastUpdated": "2019-07-03T16:28:20.526+05:30"
}

```

**GET /api/stocks/1**
```
Request: 

curl -X GET \
  http://localhost:8080/api/stocks/1 \
  -H 'Accept: application/json' \
  -H 'Authorization: Basic c3RvY2t5OnN0b2NreQ==' \
  -H 'cache-control: no-cache'
  
Response:

{
  "stockId": 1,
  "stockName": "payconiqStock",
  "currentPrice": 16.6,
  "created": "2019-07-03T16:28:20.526+05:30",
  "lastUpdated": "2019-07-03T16:28:20.526+05:30"
}
```

**GET /api/stocks/**
```
Request: 

curl -X GET \
  'http://localhost:8080/api/stocks/?page=0&size=2' \
  -H 'Accept: application/json' \
  -H 'Authorization: Basic c3RvY2t5OnN0b2NreQ==' \
  -H 'cache-control: no-cache'
  
Response:

{
    "stocksDetailList": [
        {
            "stockId": 1,
            "stockName": "payconiqStock",
            "currentPrice": 16.6,
            "created": "2019-07-03T16:28:20.526+05:30",
            "lastUpdated": "2019-07-03T16:28:20.526+05:30"
        },
        {
            "stockId": 2,
            "stockName": "NASDAQ:AAPL",
            "currentPrice": 25.89,
            "created": "2019-07-03T16:39:29.249+05:30",
            "lastUpdated": "2019-07-03T16:39:29.249+05:30"
        }
    ],
    "size": 2,
    "number": 0,
    "totalPages": 1,
    "totalElements": 2
}
```

**DELETE /api/stocks/1**
```
Request: 

curl -X DELETE \
  http://localhost:8080/api/stocks/1 \
  -H 'Accept: application/json' \
  -H 'Authorization: Basic c3RvY2t5OnN0b2NreQ==' \
  -H 'cache-control: no-cache'
  
Response:

204 No Content 
```

**PUT /api/stocks/1**
```
Request:

curl -X PUT \
  http://localhost:8080/api/stocks/2 \
  -H 'Accept: application/json' \
  -H 'Authorization: Basic c3RvY2t5OnN0b2NreQ==' \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
  "currentPrice": 95.4
}'

Response:

{
    "stockId": 2,
    "stockName": "NASDAQ:AAPL",
    "currentPrice": 95.4,
    "created": "2019-07-03T16:39:29.249+05:30",
    "lastUpdated": "2019-07-03T19:36:41.335+05:30"
}
```

## Actuator Endpoints
Insights and metrics about running application of stocky.

* *http://localhost:8080/actuator/auditevents*  : Exposes audit events information for the current application.
* *http://localhost:8080/actuator/beans*  : Returns a complete list of all the Spring beans in your application.
* *http://localhost:8080/actuator/health* : Returns application health information. 
* *http://localhost:8080/actuator/info*   : Displays arbitrary application info.
* *http://localhost:8080/actuator/heapdump* : Returns an hprof heap dump file.
* *http://localhost:8080/actuator/threaddump* : Performs a thread dump
* *http://localhost:8080/actuator/metrics*  : Shows ‘metrics’ information for the current application.

## Dependencies

*Java*: v1.8\
*Spring Boot*: v2.1.6\
*Swagger*:  v2.0\
*Maven*:  v3.6.1\
*H2*:  v1.4.193\
*lombok*:  v1.16.20\
*modelmapper*:  v2.3.2\
*Actuator*:  v2.1.6

## Plugins

*swagger-codegen*:  v2.2.3\
*jacoco*:  v0.8.0

## Future Enhancements:

* **Vault Integration** : Currently application properties and secrets are hardcoded, when the data gets sensitive and important we can integrate with Hashicorp’s Vault to secure sensitive configuration data. 
* **Caching layer**: Implementing a caching layer below the service layer when the scale goes up.
* **Role Based Authorization** : Currently there is only authentication and no authorization implemented. This can be implemented for role based resource access.
* **CI/CD Pipeline** : Continuous integration and deployment (CI/CD) pipeline to establish a consistent and automated way to build, package and test the application. Repo badges can be added for code coverage/latest build which updates after every commit.  
* **Web interface** : Interface to display stocks with pagination.
