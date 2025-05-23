# Etapa 1: Build usando Maven y JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia solo pom.xml para aprovechar cache de dependencias
COPY pom.xml .

# Descarga dependencias sin compilar aún (cache)
RUN mvn dependency:go-offline

# Copia el código fuente
COPY src ./src

# Compila y empaqueta el .jar, sin tests
RUN mvn clean package -DskipTests

# Etapa 2: Runtime con JDK 21 ligero
FROM eclipse-temurin:21.0.4-jdk

WORKDIR /app

# Copia el .jar desde la etapa builder
COPY --from=builder /app/target/horario-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto (ajusta si usas otro)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]

