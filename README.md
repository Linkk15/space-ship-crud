# SpaceSip CRUD microservice

In this microservice you can make the CRUD operations for manage Space Ships.

## Authors

- [@adrian.parra](https://github.com/Linkk15)

## Features

- Spring Boot 3
- Java 21
- Spring Reactor (WebFlux)
- MapStruct
- H2 Database
- ArchUnit (testing architecture library)

## Installation

Clone or download zip code

```bash
  mvn clean install
```

## Running Tests

To run tests, run the following command

```bash
  mvn test
```

## Execute

To run application in your local environment execute this command

```bash
  docker build -t space-ship-crud:latest .
  docker run -p 8080:8080 space-ship-crud:latest
```