package pl.adrian.catalogservice.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import pl.adrian.catalogservice.models.Comment;
import pl.adrian.catalogservice.models.MovieInfo;
import pl.adrian.catalogservice.models.Rating;
import pl.adrian.catalogservice.services.CatalogServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CatalogController.class)
@Import(ReactiveResilience4JAutoConfiguration.class)
class CatalogControllerTest {

    @MockBean
    private CatalogServiceImpl catalogService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getMovieItem() {
        Rating rating = new Rating("1", "1", 5);
        Comment comment = new Comment("1", "1", "comment");
        MovieInfo movieInfo = new MovieInfo("1", "title", "description");

        Mockito.when(catalogService.getRating(anyString())).thenReturn(Mono.just(rating));
        Mockito.when(catalogService.getMovieInfo(anyString())).thenReturn(Mono.just(movieInfo));
        Mockito.when(catalogService.getMovieComments(anyString())).thenReturn(Flux.just(comment));

        webTestClient.get()
                .uri("/catalog/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.comments[0].content").isEqualTo("comment")
                .jsonPath("$.rating").isEqualTo(5)
                .jsonPath("$.title").isEqualTo("title")
                .jsonPath("$.desc").isEqualTo("description");
    }
}