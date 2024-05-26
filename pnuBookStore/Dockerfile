# 1. build
FROM gradle:7.5.1-jdk17 as build

WORKDIR /home/gradle/project

COPY . .

RUN gradle wrapper
RUN ./gradlew clean build -x test

# 2. Run the application
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /opt/cloud

COPY --from=build /home/gradle/project/build/libs/cloud.jar .

ENV DATABASE_URI=${DATABASE_URI:-}
ENV DATABASE_USERNAME=${DATABASE_USERNAME:-}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD:-}
ENV MAIL_USERNAME=${MAIL_USERNAME:-}
ENV MAIL_PASSWORD=${MAIL_PASSWORD:-}
ENV REDIS_URI=${REDIS_URI:-}
ENV REDIS_PASSWORD=${REDIS_PASSWORD:-}
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "cloud.jar"]