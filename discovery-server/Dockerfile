FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8761
CMD echo "The application will start in 10s..." && \
    sleep 15 && \
    java ${JAVA_OPTS} -jar /app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]