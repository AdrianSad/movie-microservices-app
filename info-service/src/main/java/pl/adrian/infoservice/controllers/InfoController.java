package pl.adrian.infoservice.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import pl.adrian.infoservice.models.MovieInfo;
import pl.adrian.infoservice.models.MovieSummary;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
public class InfoController {

    private final WebClient.Builder webClientBuilder;

    @Value("${theMovieDBApiKey}")
    private String apiKey;

    public InfoController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @RequestMapping("/{movieId}")
    public Mono<MovieInfo> getMovieInfo(@PathVariable String movieId){

        return webClientBuilder
                .build()
                .get()
                .uri("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey)
                .retrieve()
                .bodyToMono(MovieSummary.class)
                .map(movieSummary -> new MovieInfo(movieId, movieSummary.getTitle(), movieSummary.getOverview()));
    }
}
