package pl.adrian.ratingsservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.ratingsservice.models.Rating;
import pl.adrian.ratingsservice.repositories.RatingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingRepository ratingRepository;

    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @GetMapping("/{movieId}")
    public Mono<Rating> getRating(@PathVariable String movieId){
        return ratingRepository.findByMovieId(movieId);
    }

}
