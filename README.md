## Configure Spring Datasource, JPA, App properties
Open `src/main/resources/application.properties`

## Run Spring Boot application
```
mvn spring-boot:run
```

## Run following SQL insert statements
```
INSERT INTO roles(name) VALUES('ROLE_CUSTOMER');
INSERT INTO roles(name) VALUES('ROLE_STAFF');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```
