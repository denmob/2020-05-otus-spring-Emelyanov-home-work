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
COPY --from=0 $PROJECT_DIR/target/hw17-docker-2020-05-SNAPSHOT.jar $PROJECT_DIR/

EXPOSE 8080
CMD ["java","-jar","/opt/project/hw17-docker-2020-05-SNAPSHOT.jar"]
