FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} health-tracker/heart-rate/heart_rate.war

EXPOSE 8081
WORKDIR health-tracker/heart-rate

ENTRYPOINT ["java", "-jar", "heart_rate.war"]