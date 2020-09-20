package pl.adrian.catalogservice.services;

import pl.adrian.catalogservice.models.Comment;
import pl.adrian.catalogservice.models.MovieInfo;
import pl.adrian.catalogservice.models.Rating;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CatalogService {

     Mono<Rating> getRating(String movieId);

     Mono<MovieInfo> getMovieInfo(String movieId);

     Flux<Comment> getMovieComments(String movieId);
}
