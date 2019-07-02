# Stocky Service
*Stocky* is a RESTful API on Spring Boot built with a top-down approach (First API specs and then code generation.)  

## API First: Swagger Top-Down approach
Started with the API contract using OpenAPI specification(OAS) [stocky-service.yml](/src/main/resources/swagger/stocky-service.yml), and then *swagger-codegen* has been used to generate the client code stubs.

## Endpoints

#### POST
*/api/stocks* : To Create a stock.

#### PUT
*/api/stocks/{stockId}* : To Update a existing stock identified by stockId.

#### GET
*/api/stocks/{stockId}* : To get details of a stock identified by stockId.\
*/api/stocks/* : To get paginated list of stocks.

Refer: [stocky-service.yml](/src/main/resources/swagger/stocky-service.yml) for more details.

## Build 
git clone https://github.com/mrajesh016/stocky.git
run `mvn clean install` 

## Run Stocky 
run `mvn spring-boot:run`      

## Docker Build Image
make build

## Docker Run Container
make run

### Swagger UI
http://localhost:8080/swagger-ui.html

## Integration & Unit Tests
`mvn clean verify` to run all unit and integration tests.

## Code Coverage
view unit tests code coverage report at: `target/site/jacoco/index.html`.

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
* Currently application properties and secrets are hardcoded, when the data gets sensitive and important we can integrate with Hashicorp’s Vault to secure sensitive configuration data. 
* All the endpoints are currently secured with *Basic access authentication*, better secuirty can be provided with Json Web Token(JWT) based spring security.
* Web interface to display stocks with pagination.
* Implementing a caching layer below the service layer when the scale goes up.
