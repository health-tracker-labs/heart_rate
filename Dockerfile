#build
FROM maven:3.9-eclipse-temurin-21 AS build

ARG APP_HOME=/health-tracker/heart-rate
WORKDIR ${APP_HOME}

COPY pom.xml .
COPY src ./src

RUN mvn clean package

#deploy
FROM eclipse-temurin:21-jre-jammy

ARG APP_HOME=/health-tracker/heart-rate
ARG JAR_FILE=${APP_HOME}/target/*.war

WORKDIR ${APP_HOME}
COPY --from=build ${JAR_FILE} heart_rate.war

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "heart_rate.war"]