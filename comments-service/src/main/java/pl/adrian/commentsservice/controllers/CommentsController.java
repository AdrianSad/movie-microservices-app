package pl.adrian.commentsservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.adrian.commentsservice.models.Comment;
import pl.adrian.commentsservice.models.CommentRequest;
import pl.adrian.commentsservice.repositories.CommentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final CommentRepository commentRepository;

    public CommentsController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @GetMapping("/{movieId}")
    public Flux<Comment> getMovieComments(@PathVariable String movieId) {

        return commentRepository.findAllByMovieId(movieId);
    }

    @PostMapping
    public Mono<Comment> createMovieItem(@RequestBody CommentRequest commentRequest) {

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setMovieId(commentRequest.getMovieId());
        return commentRepository.save(comment);
    }
}
