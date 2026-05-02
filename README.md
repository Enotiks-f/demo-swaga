# demo-swaga

Учебный backend-проект на Spring Boot: REST API для студентов, факультетов и аватаров с PostgreSQL, Liquibase и Swagger.

## Что внутри

- CRUD для `Student` и `Faculty`
- загрузка и выдача аватаров студентов
- пагинация для списка аватаров
- сервисные эндпоинты (`/port`, `/api/sum`)
- автодокументация API через OpenAPI/Swagger

## Технологии

- Java 17
- Spring Boot 3.5
- Spring Web, Spring Data JPA
- PostgreSQL
- Liquibase
- springdoc-openapi
- Maven

## Быстрый старт

1. Поднимите PostgreSQL и создайте БД `hogwarts`.
2. Проверьте настройки в `src/main/resources/application.properties`:
   - `spring.datasource.url`
   - `spring.datasource.username`
   - `spring.datasource.password`
3. Запустите приложение:

```powershell
.\mvnw.cmd spring-boot:run
```

Приложение стартует на `http://localhost:8082`.

## Профили

- по умолчанию: порт `8082`
- `dev`: порт `8080`
- `prod`: порт `9090`

Пример запуска с профилем:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

## API и документация

- Swagger UI: [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html)
- OpenAPI JSON: [http://localhost:8082/v3/api-docs](http://localhost:8082/v3/api-docs)

Ключевые группы эндпоинтов:

- `/student` — студенты, фильтрация по возрасту, статистика, работа с аватарами
- `/faculty` — факультеты и список студентов факультета
- `/avatar` — постраничный список аватаров
- `/port`, `/api/sum` — служебные методы

## Тесты

```powershell
.\mvnw.cmd test
```

## Структура проекта

- `src/main/java/ruhogwarts/school/controller` — REST-контроллеры
- `src/main/java/ruhogwarts/school/service` — бизнес-логика
- `src/main/java/ruhogwarts/school/repository` — JPA-репозитории
- `src/main/java/ruhogwarts/school/model` — сущности
- `src/main/resources/liquibase` — миграции БД
