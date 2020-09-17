package pl.adrian.catalogservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.catalogservice.models.Movie;
import pl.adrian.catalogservice.models.MovieInfo;
import pl.adrian.catalogservice.models.Rating;
import pl.adrian.catalogservice.services.CatalogService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @RequestMapping("/{movieId}")
    public Mono<Movie> getMovieItem(@PathVariable String movieId) {


        Mono<Rating> ratings = catalogService.getRating(movieId);

        Mono<MovieInfo> movieInfo = catalogService.getMovieInfo(movieId);


        return Mono.zip(ratings, movieInfo).map(data -> {
            Movie tempMovie = new Movie();
            tempMovie.setMovieId(movieId);
            tempMovie.setRating(data.getT1().getRating());
            tempMovie.setTitle(data.getT2().getTitle());

            return tempMovie;
        });
    }




}
