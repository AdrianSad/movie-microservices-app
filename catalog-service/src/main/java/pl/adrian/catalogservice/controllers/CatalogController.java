package pl.adrian.catalogservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.catalogservice.models.Comment;
import pl.adrian.catalogservice.models.Movie;
import pl.adrian.catalogservice.models.MovieInfo;
import pl.adrian.catalogservice.models.Rating;
import pl.adrian.catalogservice.services.CatalogService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/catalog")
@Slf4j
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/{movieId}")
    public Mono<Movie> getMovieItem(@PathVariable String movieId) {

        Mono<Rating> ratings = catalogService.getRating(movieId);
        Mono<MovieInfo> movieInfo = catalogService.getMovieInfo(movieId);
        Flux<Comment> comments = catalogService.getMovieComments(movieId);
        Mono<List<Comment>> commentListMono = comments.collectList();

        return Mono.zip(ratings, movieInfo, commentListMono).map(data -> {
            Movie tempMovie = new Movie();
            tempMovie.setMovieId(movieId);
            tempMovie.setRating(data.getT1().getRating());
            tempMovie.setTitle(data.getT2().getTitle());
            tempMovie.setDesc(data.getT2().getDesc());
            tempMovie.setComments(data.getT3());
            return tempMovie;
        });
    }
}
