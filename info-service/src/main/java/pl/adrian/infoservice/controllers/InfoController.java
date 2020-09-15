package pl.adrian.infoservice.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.infoservice.models.MovieInfo;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
public class InfoController {

    @RequestMapping("/{movieId}")
    public Mono<MovieInfo> getMovieInfo(@PathVariable String movieId){
        return Mono.just(new MovieInfo(movieId, "title from info", "desc test", "Quentin Tarantino", "USA"));
    }
}
