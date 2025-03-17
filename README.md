# Account Microservice

This is a Spring Boot-based microservice for managing accounts. The project is configured with Maven and uses various Spring Boot starters for web, data, validation, and actuator functionalities.


## Configuration

### `pom.xml`

The `pom.xml` file includes the following dependencies:

- Spring Boot Starter Actuator
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Web
- Spring Boot DevTools
- MySQL Connector
- Lombok
- Spring Boot Starter Test

The project is set to use Java 17.

### `application.yml`

The `application.yml` file includes the following configurations:

- **Spring Application Name**: Account Microservice
- **Datasource Configuration**:
  - URL: `jdbc:mysql://${DATABASE_HOST:localhost}:${DATABASE_PORT:3306}/${DATABASE_NAME:microservice}`
  - Username: `${DATABASE_USERNAME:root}`
  - Password: `${DATABASE_PASSWORD:root}`
- **JPA Configuration**:
  - Hibernate DDL Auto: `update`
  - Show SQL: `true`
  - Format SQL: `true`
- **Logging Configuration**:
  - Console Pattern: `%green(%d{HH:mm:ss.SSS}) %blue(%-5level) %red([%thread]) %yellow(%logger{15}) - %msg%n`
  - Hibernate SQL Level: `DEBUG`
  - Hibernate Type Descriptor SQL Level: `TRACE`

## Running the Application

To run the application, use the following command:

```sh
./mvnw spring-boot:run
```

## Testing

To run the tests, use the following command:

```sh
./mvnw test
```

## Maven Wrapper

The project includes Maven Wrapper scripts (`mvnw` and `mvnw.cmd`) to ensure that the correct version of Maven is used.

## License

This project is licensed under the Apache License, Version 2.0. See the [LICENSE](http://www.apache.org/licenses/LICENSE-2.0) file for details.