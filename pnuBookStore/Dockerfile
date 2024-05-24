# 1. build
FROM gradle:7.5.1-jdk17 as build

WORKDIR /home/gradle/project

COPY . .

RUN gradle wrapper
RUN ./gradlew clean build

# 2. Run the application
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /opt/cloud

COPY --from=build /home/gradle/project/build/libs/cloud.jar .

ENV DATABASE_URI=${DATABASE_URI}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}

CMD ["java", "-Dspring.profiles.active=prod", "-jar", "cloud.jar"]