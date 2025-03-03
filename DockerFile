FROM maven:3-eclipse-temurin-17 AS build
WORKDIR /app
# Cambia al directorio correcto
COPY ./ /app
# Copia los archivos dentro del contenedor
WORKDIR /app
# Entra en el directorio correcto
RUN mvn clean package -DskipTests
FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
