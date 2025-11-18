FROM gradle:8.6-jdk21 AS builder
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
RUN gradle clean bootJar --no-daemon

FROM eclipse-temurin:21-jre-jammy
ARG JAR_FILE=/home/gradle/project/build/libs/*.jar
COPY --from=builder ${JAR_FILE} /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
