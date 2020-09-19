FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#
#FROM openjdk:8
#ADD target/discovery-server.jar discovery-server.jar
#EXPOSE 8761
#ENTRYPOINT java -jar discovery-server-0.0.1-SNAPSHOT.jar discovery-server