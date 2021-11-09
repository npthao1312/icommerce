## Simple Ecommerce App

## Prerequisites
- Java
- JDK
- Maven
- MySQL

## Swagger
Access Swagger endpoints at `http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/`

## Configure Spring Datasource, JPA, App properties
Open `src/main/resources/application.properties`

## Run Spring Boot application
```
mvn spring-boot:run
```

## Run following SQL insert statements
```
CREATE DATABASE testdb;
INSERT INTO roles(name) VALUES('ROLE_CUSTOMER');
INSERT INTO roles(name) VALUES('ROLE_STAFF');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```


## Get an executable contains all the resources and dependencies Jar file
```
mvn clean install
```