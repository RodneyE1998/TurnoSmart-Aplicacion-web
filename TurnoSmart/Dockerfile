# Usa una imagen oficial de Maven para compilar
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia el pom.xml y descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el resto del proyecto y compila
COPY . .
RUN mvn clean package -DskipTests

# Imagen final con Java 17
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiamos el .jar compilado
COPY --from=build /app/target/*.jar app.jar

# Puerto de la app
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
