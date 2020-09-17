package pl.adrian.catalogservice.services;

import pl.adrian.catalogservice.models.MovieInfo;
import pl.adrian.catalogservice.models.Rating;
import reactor.core.publisher.Mono;

public interface CatalogService {

     Mono<Rating> getRating(String movieId);

     Mono<MovieInfo> getMovieInfo(String movieId);
}
