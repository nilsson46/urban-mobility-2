#FROM openjdk:17
#ARG JAR_FILE=target/urban-mobility-1.0.jar
#COPY ${JAR_FILE} /application.jar
#EXPOSE 8081
#ENTRYPOINT ["java", "-jar", "/application.jar"]

# syntax=docker/dockerfile:1

FROM openjdk:17 as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base as test
CMD ["./mvnw", "test"]

FROM base as development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM base as build
RUN ./mvnw package


FROM openjdk:17 as production
EXPOSE 8081
COPY --from=build /app/target/urban-mobility-*.jar /urban-mobility.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/urban-mobility.jar"]