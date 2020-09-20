#FROM openjdk:8-jdk-alpine
#
#MAINTAINER Adrian Sadurski
#VOLUME /tmp
#COPY target/*.jar /app.jar
#
#EXPOSE 8084
#ENTRYPOINT java -jar app.jar

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8083
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]