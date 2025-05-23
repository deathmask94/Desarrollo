# Usa la imagen oficial de OpenJDK 21 como base
FROM eclipse-temurin:21-jdk-alpine

# Crea un directorio para la app
WORKDIR /app

# Copia el .jar generado por Spring Boot
COPY target/tu-app-horario.jar app.jar

# Expone el puerto que tu app usa (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
