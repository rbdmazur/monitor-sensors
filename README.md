# Sensor Monitor
## Используемые технологии:
* Spring Boot
* Spring Seсurity
* Spring Data JPA
* Hibernate
* PostgreSQL
## Запуск
Для запуска приложения используется Gradle.
Перед запуском необходимо сконфигурировать БД. Скрипты создания таблиц находятся в папке ```src/main/resources/sql```.
В ```application.properties``` прописать кофнигурацию БД:
```
spring.datasource.url=database_url
spring.datasource.username=postgres
spring.datasource.password=postgres
```
## Документация
Документация находится по эндпоинту ```http://localhost:8080/swagger-ui.html```
