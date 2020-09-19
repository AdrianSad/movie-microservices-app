package pl.adrian.commentsservice;

import com.mongodb.MongoDriverInformation;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import pl.adrian.commentsservice.repositories.CommentRepository;

@SpringBootApplication
@EnableEurekaClient
public class CommentsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentsServiceApplication.class, args);
	}

}
