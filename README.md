# Bank Application

A learning pet project built with Spring Boot, simulating a simple banking application:
user registration, account management, money transfers, and currency conversion.

⸻

## Features
	•	User registration and authentication
	•	Creating and managing bank accounts
	•	Depositing and withdrawing funds
	•	Transfers between accounts
	•	Currency conversion (USD ↔ EUR ↔ RUB)
	•	Error handling via @ControllerAdvice and custom exceptions
	•	API documentation through Swagger UI

⸻

## Technology Stack
	•	Java 17
	•	Spring Boot (Web, Security, Data JPA)
	•	Hibernate
	•	MySQL
	•	Swagger / OpenAPI
	•	Maven
  
---

## Running the Project

### 1. Clone the repository
git clone https://github.com/your-username/bank-app.git
cd bank-app

### 2. Configure the database
Create a database in MySQL:
```CREATE DATABASE bank;```

### 3. Create a .env file
In src/main/resources, create a .env file and add your credentials:
```
DB_USERNAME=root
DB_PASSWORD=rootroot
```

### 4. Check application.properties
```
spring.application.name=bank
server.port=8080
server.address=0.0.0.0

spring.datasource.url=jdbc:mysql://localhost:3306/bank
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 5. Run the application
``` mvn spring-boot:run ```

### 6. Swagger UI

After starting the application, the documentation will be available at:
http://localhost:8080/swagger-ui.html


## API Examples

User Registration
```
POST /api/auth/register
{
  "username": "artur",
  "password": "123456"
}
```

Transfer Funds
```
POST /api/accounts/transfer
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 100.00
}
```

 ---
 Author: Artur (Telegram: @wskywalk)
