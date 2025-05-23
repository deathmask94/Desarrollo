# Etapa 1: Build con Maven y JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2: Runtime con JDK 21 oficial, tag simplificado
FROM eclipse-temurin:21.0.4-jdk

WORKDIR /app

COPY --from=builder /app/target/horario-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
