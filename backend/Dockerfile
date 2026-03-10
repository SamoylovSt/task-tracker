# Stage 1: Сборка приложения
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Копируем pom.xml и скачиваем зависимости (кэширование)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходный код и собираем приложение
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Запуск приложения
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Копируем собранный jar из первого этапа
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]