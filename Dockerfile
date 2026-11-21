FROM maven:3.9-eclipse-temurin-25-alpine AS build

RUN mkdir /project

COPY . /project

# Passed from Github Actions
ARG GIT_VERSION_TAG=unspecified
ARG GIT_COMMIT_MESSAGE=unspecified
ARG GIT_VERSION_HASH=unspecified

WORKDIR /project

# You can read these files for the information in your application
RUN echo $GIT_VERSION_TAG > GIT_VERSION_TAG.txt
RUN echo $GIT_COMMIT_MESSAGE > GIT_COMMIT_MESSAGE.txt
RUN echo $GIT_VERSION_HASH > GIT_VERSION_HASH.txt

RUN mvn --batch-mode --no-transfer-progress clean package -DskipTests

FROM amazoncorretto:25-alpine-jdk
LABEL maintainer="hendisantika@yahoo.co.id"

RUN mkdir /app

RUN addgroup -g 1001 -S hendigroup

RUN adduser -S hendi -u 1001

COPY --from=build /project/target/spring-boot-postgres-debezium-0.0.1.jar /app/debezium.jar

WORKDIR /app

RUN chown -R hendi:hendigroup /app

CMD java $JAVA_OPTS -jar debezium.jar
