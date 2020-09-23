package pl.adrian.commentsservice.controllers;

import com.netflix.discovery.converters.Auto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import pl.adrian.commentsservice.models.Comment;
import pl.adrian.commentsservice.models.CommentRequest;
import pl.adrian.commentsservice.repositories.CommentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CommentsController.class)
class CommentsControllerTest {

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentsController commentsController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(){
        webTestClient = WebTestClient.bindToController(commentsController).build();
    }

    @Test
    void getMovieComments() throws Exception {
        String movieId = "1";
        Comment comment = new Comment("1", movieId, "This is a test comment");

        given(commentRepository.findAllByMovieId(anyString())).willReturn(Flux.just(comment));

        webTestClient
                .get()
                .uri("/comments/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].movieId").isEqualTo("1")
                .jsonPath("$[0].content").isEqualTo("This is a test comment");

        verify(commentRepository, times(1)).findAllByMovieId(anyString());
    }

    @Test
    void createMovieItem() throws Exception {

        CommentRequest commentRequest = new CommentRequest("1", "test");

        webTestClient.post()
                .uri("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .body(Mono.just(commentRequest), CommentRequest.class)
                .exchange()
                .expectStatus().isOk();

    }
}