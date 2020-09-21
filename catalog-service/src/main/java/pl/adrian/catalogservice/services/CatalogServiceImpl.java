package pl.adrian.catalogservice.services;

import com.netflix.hystrix.HystrixCommandProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.adrian.catalogservice.models.Comment;
import pl.adrian.catalogservice.models.MovieInfo;
import pl.adrian.catalogservice.models.Rating;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService {

    private final WebClient.Builder webClientBuilder;
    private final ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    private final HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties
            .defaultSetter()
            .withExecutionTimeoutInMilliseconds(1500)
            .withCircuitBreakerRequestVolumeThreshold(5)
            .withCircuitBreakerErrorThresholdPercentage(50)
            .withCircuitBreakerSleepWindowInMilliseconds(5000);

    public CatalogServiceImpl(WebClient.Builder webClientBuilder, ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory) {
        this.webClientBuilder = webClientBuilder;
        this.reactiveCircuitBreakerFactory = reactiveCircuitBreakerFactory;
    }

    public Mono<Rating> getRating(String movieId) {
        return webClientBuilder
                .build()
                .get()
                .uri("http://ratings-service/ratings/" + movieId)
                .retrieve()
                .bodyToMono(Rating.class)
                .defaultIfEmpty(new Rating("0", "0", 0))
                .transform(it -> {
                    ReactiveCircuitBreaker reactiveCircuitBreaker = reactiveCircuitBreakerFactory.create("rating");
                    return reactiveCircuitBreaker.run(it, throwable -> Mono.just(new Rating("0", "0", 0)));
                });
    }

    public Mono<MovieInfo> getMovieInfo(String movieId) {
        return webClientBuilder
                .build()
                .get()
                .uri("http://info-service/movies/" + movieId)
                .retrieve()
                .bodyToMono(MovieInfo.class)
                .transform(it -> {
                    ReactiveCircuitBreaker reactiveCircuitBreaker = reactiveCircuitBreakerFactory.create("info");
                    return reactiveCircuitBreaker.run(it, throwable -> Mono.just(new MovieInfo("0", "No movie info", "")));
                });
    }

    @Override
    public Flux<Comment> getMovieComments(String movieId) {
        return (webClientBuilder
                .build()
                .get()
                .uri("http://comments-service/comments/" + movieId)
                .retrieve()
                .bodyToFlux(Comment.class))
                .switchIfEmpty(Flux.empty())
                .transform(it -> {
                    ReactiveCircuitBreaker reactiveCircuitBreaker = reactiveCircuitBreakerFactory.create("comment");
                    return reactiveCircuitBreaker.run(it, throwable -> Flux.empty());
                });
    }
}
