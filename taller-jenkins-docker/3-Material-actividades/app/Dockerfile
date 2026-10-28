# Etapa 1: compilar y empaquetar con Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /src
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q -DskipTests package

# Etapa 2: imagen final, solo el JRE y el .jar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /src/target/taller-app.jar app.jar
EXPOSE 8000
CMD ["java", "-jar", "app.jar"]
