FROM openjdk:17
ARG JAR_FILE=target/urban-mobility-1.0.jar
COPY ${JAR_FILE} /application.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/application.jar"]