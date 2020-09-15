package pl.adrian.ratingsservice.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.ratingsservice.models.Rating;
import pl.adrian.ratingsservice.models.UserRating;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @RequestMapping("/{movieId}")
    public Mono<Rating> getRating(@PathVariable String movieId){
        return Mono.just(new Rating(movieId, 5));
    }

    @RequestMapping("/users/{userId}")
    public Flux<Rating> getUserRatings(@PathVariable String userId){

        return Flux.just(
                new Rating("1", 5),
                new Rating("2", 2)
        );
    }
}
