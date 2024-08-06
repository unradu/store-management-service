# Store Management Service (Demo App)
Spring Boot demo application providing some basic functionalities for the online management of a store.

## Requirements
Make sure you have the following tools installed on your machine:

- JRE 21
- Maven
- Docker Engine

## How to run the app on your machine
Run the docker container from src/test/docker-resources by executing the following command
inside the directory:

`docker-compose up`

Open your preferred terminal and run the following command:

`mvn spring-boot:run`

You can verify the health status of the application under the following URL:
http://localhost:8080/actuator/health

## Using the API

The Open API specification is available under the following url:
http://localhost:8080/v3/api-docs

Swagger UI can be used to read the endpoint documentation and can be used for triggering requests:
http://localhost:8080/swagger-ui/index.html

### Authentication
The application uses Basic Authentication and stores the user credentials in-memory.
The following users are currently configured:

| Username | Password | Role            |
|----------|----------|-----------------|
| admin    | password | ADMIN           |
| sales    | password | SALES_ASSOCIATE |

The ADMIN role can access any of the application endpoints.
The SALES_ASSOCIATE role is authorized to access the following endpoints:
- GET /api/v1/products
- GET /api/v1/products/{productId}
- POST /api/v1/orders

## Executing tests
In order to run the tests execute the following command:

`mvn clean verify`

This will execute both the unit tests and integration tests. The integration tests will spin up
a MySQL Docker container prior to their execution.