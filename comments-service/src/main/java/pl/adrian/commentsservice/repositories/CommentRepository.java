package pl.adrian.commentsservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pl.adrian.commentsservice.models.Comment;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findAllByMovieId(String movieId);
}
