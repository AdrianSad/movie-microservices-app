package pl.adrian.catalogservice.services;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import pl.adrian.catalogservice.models.MovieInfo;
import pl.adrian.catalogservice.models.Rating;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService{

    private final WebClient.Builder webClientBuilder;

    private final HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties
            .defaultSetter()
            .withExecutionTimeoutInMilliseconds(1500)
            .withCircuitBreakerRequestVolumeThreshold(5)
            .withCircuitBreakerErrorThresholdPercentage(50)
            .withCircuitBreakerSleepWindowInMilliseconds(5000);

    public CatalogServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Rating> getRating(String movieId) {
        Mono<Rating> ratingMono = (webClientBuilder
                .build()
                .get()
                .uri("http://ratings-service/ratings/" + movieId)
                .retrieve()
                .bodyToMono(Rating.class));

        return HystrixCommands.from(ratingMono)
                .commandName("movieRating")
                .commandProperties(commandProperties)
                .fallback(Mono.just(new Rating("0", 0)))
                .toMono();
    }

    public Mono<MovieInfo> getMovieInfo(String movieId) {
        Mono<MovieInfo> infoMono = (webClientBuilder
                .build()
                .get()
                .uri("http://info-service/movies/" + movieId)
                .retrieve()
                .bodyToMono(MovieInfo.class));

        return HystrixCommands.from(infoMono)
                .commandName("movieInfo")
                .commandProperties(commandProperties)
                .fallback(Mono.just(new MovieInfo("0", "No movie info", "")))
                .toMono();
    }
}
