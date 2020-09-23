package pl.adrian.ratingsservice.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.adrian.ratingsservice.models.Rating;
import pl.adrian.ratingsservice.repositories.RatingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = RatingController.class)
class RatingControllerTest {

    @MockBean
    private RatingRepository ratingRepository;

    @Autowired
    RatingController ratingController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(){
        webTestClient = WebTestClient.bindToController(ratingController).build();
    }


    @Test
    void getRating() {

        Rating rating = new Rating("1", "1", 5);

        given(ratingRepository.findByMovieId(anyString())).willReturn(Mono.just(rating));

        webTestClient
                .get()
                .uri("/ratings/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.movieId").isEqualTo("1")
                .jsonPath("$.rating").isEqualTo(5);

        verify(ratingRepository, times(1)).findByMovieId(anyString());
    }

}