package pl.adrian.commentsservice.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.commentsservice.models.Comment;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @RequestMapping("/{movieId}")
    public Flux<Comment> getMovieItem(@PathVariable String movieId) {

        return Flux.just(new Comment(movieId, "comment"));
    }
}
