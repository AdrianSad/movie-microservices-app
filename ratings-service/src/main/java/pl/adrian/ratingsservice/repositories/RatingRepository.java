package pl.adrian.ratingsservice.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.adrian.ratingsservice.models.Rating;
import reactor.core.publisher.Mono;

public interface RatingRepository extends ReactiveMongoRepository<Rating, String> {

    Mono<Rating> findByMovieId(String movieId);
}
