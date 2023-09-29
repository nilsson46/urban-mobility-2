#FROM openjdk:17
#ARG JAR_FILE=target/urban-mobility-1.0.jar
#COPY ${JAR_FILE} /application.jar
#EXPOSE 8081
#ENTRYPOINT ["java", "-jar", "/application.jar"]

# syntax=docker/dockerfile:1

# syntax=docker/dockerfile:1

# syntax=docker/dockerfile:1



# Use a Maven-specific base image
FROM maven:3.8.4-openjdk-17-jdk-slim as base
WORKDIR /
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Ensure the script has LF line endings
RUN sed -i 's/\r$//' ./mvnw && chmod +x ./mvnw
# Use the correct command to resolve dependencies
CMD ["./mvnw", "dependency:resolve"]
COPY src ./src

# Consolidate build and test stages
FROM base as build
# Build the application
CMD ["./mvnw", "package"]

# Production stage
FROM eclipse-temurin:17-jre-jammy as production
EXPOSE 8080
# Copy the built JAR file into the image
COPY target/urban-mobility-1.0.jar /urban-mobility-1.0.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/urban-mobility-1.0.jar"]