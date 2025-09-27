# Bank Application

Учебный pet-проект, реализованный на **Spring Boot**, моделирующий работу простого банковского приложения:  
регистрация пользователей, открытие счетов, переводы между ними и конвертация валют.  

---

## Функционал
- Регистрация и авторизация пользователей  
- Создание и управление банковскими счетами  
- Пополнение и снятие средств  
- Переводы между счетами  
- Конвертация валют (USD ↔ EUR ↔ RUB)  
- Обработка ошибок через `@ControllerAdvice` и кастомные исключения  
- Документация API через **Swagger UI**  

---

## Стек технологий
- **Java 17**  
- **Spring Boot** (Web, Security, Data JPA)  
- **Hibernate**  
- **MySQL**  
- **Swagger / OpenAPI**  
- **Maven**  

---

## Архитектура проекта
src/main/java/com/skyw/bankApp
│── controller      → REST-контроллеры
│── service         → бизнес-логика
│── repository      → работа с БД
│── dto             → Data Transfer Objects
│── exception       → кастомные исключения
│── config          → конфигурации (Security, Swagger)

---

## Запуск проекта

### 1. Клонировать репозиторий
git clone https://github.com/your-username/bank-app.git
cd bank-app

### 2. Настроить базу данных
Создать базу данных в MySQL:
```CREATE DATABASE bank;```

### 3. Создать .env файл
В папке src/main/resources создать файл .env и указать креды:
```
DB_USERNAME=root
DB_PASSWORD=rootroot
```

### 4. Проверить application.properties
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

### 5. Запустить приложение
``` mvn spring-boot:run ```

### 6. Swagger UI

После запуска документация будет доступна по адресу:
http://localhost:8080/swagger-ui.html


## Примеры API
Регистрация пользователя
```
POST /api/auth/register
{
  "username": "artur",
  "password": "123456"
}
```

Перевод средств
```
POST /api/accounts/transfer
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 100.00
}
```

 ---
 Автор
