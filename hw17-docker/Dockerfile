FROM maven:3.6.3-jdk-11-slim AS build

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
RUN mvn clean
RUN mvn dependency:resolve

ADD ./src/ $PROJECT_DIR/src
RUN mvn package -DskipTests=true


FROM openjdk:11-jdk-slim

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=build $PROJECT_DIR/target/hw17-docker-2020-05-SNAPSHOT.jar $PROJECT_DIR/

EXPOSE 8080
VOLUME /logs/

ENTRYPOINT  ["java","-Dmongo.db.name=hw17Docker", "-Dmongo.host=mongo", "-Dmongo.port=27017","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/project/hw17-docker-2020-05-SNAPSHOT.jar"]
