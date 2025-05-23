# Etapa 1: Construcción
FROM maven:3.9.6-openjdk-21-slim AS build

WORKDIR /app

# Copiar archivos de configuración y fuentes
COPY pom.xml .
COPY src ./src

# Descargar dependencias y compilar el proyecto
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copiar el JAR generado desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto en el que la aplicación escuchará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
