FROM maven:3.6.3-jdk-8-slim AS build-reservation
COPY /src /usr/src/spring/src
COPY pom.xml /usr/src/spring
COPY Dockerfile /usr/src/spring
RUN mvn -f /usr/src/spring/pom.xml -DskipTests=true clean install

FROM openjdk:8-jre-slim
RUN apt-get update
COPY --from=build-reservation /usr/src/spring/target/reservation-1.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]