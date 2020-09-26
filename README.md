# Movie Microservices Application

Reactive Spring application showing communication between microservices. They are connected by Eureka Service Discovery from the Spring Cloud Netflix technology stack.
Catalog service makes a call to other services for example info service with fault resistance provided by Resilience4j.
If one of services is slow or not responding Resilience4j provide a break circuit and fallback. Everything is monitored and measured in metrics by Prometheus and then displayed by Grafana.
Services are configured by Spring Clound Config. When starting with a docker compose there is a timeout 20s to fetch data from config-server. 

You can access services at given location :

 - Eureka Server : `http://localhost:8761`
 
 - Catalog Service : `http://localhost:8081`
 
 - Info Service : `http://localhost:8082`
 
 - Ratings Service : `http://localhost:8083`

 - Comments Service : `http://localhost:8084`

 - Grafana Dashboards - `http://localhost:3000`
 
 - Prometheus - `http://localhost:9091`

## Microservices Architecture Diagram

![Diagram](https://imgur.com/SbSRv1K.jpg)

## Getting started

Clone repository :

`https://github.com/AdrianSad/movie-microservices-app.git`

Insert your own theMovieDBApiKey in docker-compose.yml or Spring Cloud Config

Start every service :

`./mvnw spring-boot:run`

Or start by Docker Compose : \
Build .jar files of every service : \
`mvn clean`\
`mvn package`\
Start with docker compose :\
`docker-compose up --build`

Example api usage : 
`http://localhost:8081/catalog/100`

## Grafana dashboard overview

![Dashboard](https://imgur.com/llGV5eR.png)


## TODO list

 - Frontend service
 - Secure Spring Cloud Server access