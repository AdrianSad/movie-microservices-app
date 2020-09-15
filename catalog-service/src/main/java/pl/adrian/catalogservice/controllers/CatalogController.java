package pl.adrian.catalogservice.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import pl.adrian.catalogservice.models.Movie;
import pl.adrian.catalogservice.models.MovieInfo;
import pl.adrian.catalogservice.models.Rating;
import pl.adrian.catalogservice.models.UserRating;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final RestTemplate restTemplate;

    private final WebClient.Builder webClientBuilder;

    public CatalogController(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }

    @RequestMapping("/{movieId}")
    public Mono<Movie> getMovieItem(@PathVariable String movieId) {

        Mono<Rating> ratings = webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8083/ratings/" + movieId)
                .retrieve()
                .bodyToMono(Rating.class);

        Mono<MovieInfo> movieInfo = ratings.flatMap(rating -> webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8082/movies/" + rating.getMovieId())
                .retrieve()
                .bodyToMono(MovieInfo.class));

        return Mono.zip(ratings, movieInfo).map(data -> {
            Movie tempMovie = new Movie();
            tempMovie.setMovieId(movieId);
            tempMovie.setRating(data.getT1().getRating());
            tempMovie.setTitle(data.getT2().getTitle());

            return tempMovie;
        });
    }
}
