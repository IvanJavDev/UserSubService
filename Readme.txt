Описание
Сервис управления подписками пользователей на цифровые сервисы (Netflix, Spotify и др.) с REST API интерфейсом. Позволяет:
- Создавать/удалять пользователей
- Добавлять/удалять подписки
- Проверять активные подписки
- Получать аналитику по популярности сервисов

Технологии
- Java 17
- Spring Boot 3
- PostgreSQL
- Liquibase
- ModelMapper
- Lombok
- SLF4J (логирование)

Запуск проекта
Создать БД: (CREATE DATABASE subscription_db;)
Настроить подключение в application.yml
Сборка и запуск (mvn clean install , mvn spring-boot:run)

API Endpoints

Пользователи
POST /users - создать пользователя
GET /users/{id} - получить информацию о пользователе
PUT /users/{id} - обновить пользователя
DELETE /users/{id} - удалить пользователя

Подписки
POST /users/{id}/subscriptions - добавить подписку
GET /users/{id}/subscriptions - получить подписки пользователя
DELETE /users/{id}/subscriptions/{sub_id} - удалить подписку
GET /subscriptions/top - получить ТОП-3 популярных подписок

Миграции БД
Проект использует Liquibase для управления схемой БД. Миграции находятся в:src/main/resources/db/changelog/

Контейнеризация
Проект можно запустить в Docker:
- Собрать образ (docker build -t)
- Запустить с Docker Compose(docker-compose up)