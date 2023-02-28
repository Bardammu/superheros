# Mindata Superheros Management

## Requirements

- Java 17
- Docker (Optional)

## Building and execution

To build the project execute:
```
./mvnw clean install
```
Options:
- Skip docker build: ```-Ddockerfile.skip=true```
- Skip unit test: ```-DskipTests```
- Run integration test: ```-Pintegraion```

To run the project using Spring Boot embedded Tomcat execute:

```./mvnw spring-boot:run```

To run the project using Docker execute:

```docker run -d -p 8080:8080 mindata/superheros:0.0.1-SNAPSHOT```

## Usage
To view how to use the API you can visit the following url:

```http://localhost:8080/api-doc.html```

## Important considerations

The project implements a REST API.

All GET methods and POST methods to "/users/register" are unprotected. The other methods require Basic Authentication.

To be able to test the application, the database is initialized with some data and a registered user whose username is
"user" and its password is "password".