package pl.adrian.catalogservice.services;

import com.google.inject.internal.cglib.proxy.$Callback;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JAutoConfiguration;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import pl.adrian.catalogservice.controllers.CatalogController;
import pl.adrian.catalogservice.models.Comment;
import pl.adrian.catalogservice.models.MovieInfo;
import pl.adrian.catalogservice.models.Rating;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class CatalogServiceImplTest {

    private CatalogServiceImpl catalogService;

    private MockWebServer server;

    @BeforeEach
    void setUp() {
        server = new MockWebServer();
        catalogService = new CatalogServiceImpl(WebClient.builder(), new ReactiveResilience4JCircuitBreakerFactory());
        catalogService.setRatingServer(server.url("/").toString());
        catalogService.setCommenstServer(server.url("/").toString());
        catalogService.setInfoServer(server.url("/").toString());
    }

    @AfterEach
    void shutdown() throws IOException {
        this.server.shutdown();
    }

    @Test
    void getRating() {
        prepareResponse(mockResponse -> mockResponse
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":\"1\",\"movieId\":\"1\", \"rating\":5}"));

        Rating rating = catalogService.getRating("1").block();

        assertNotNull(rating);
        assertEquals(rating.getId(), "1");
        assertEquals(rating.getMovieId(), "1");
        assertEquals(rating.getRating(), 5);
    }

    @Test
    void getMovieInfo() {
        prepareResponse(mockResponse -> mockResponse
                .setHeader("Content-Type", "application/json")
                .setBody("{\"movieId\":\"1\",\"title\":\"test_title\", \"desc\":\"test_desc\"}"));

        MovieInfo movieInfo = catalogService.getMovieInfo("1").block();

        assertNotNull(movieInfo);
        assertEquals(movieInfo.getMovieId(), "1");
        assertEquals(movieInfo.getTitle(), "test_title");
        assertEquals(movieInfo.getDesc(), "test_desc");
    }

    @Test
    void getMovieComments() {
        prepareResponse(mockResponse -> mockResponse
                .setHeader("Content-Type", "application/json")
                .setBody("{\"id\":\"1\",\"movieId\":\"1\", \"content\":\"test\"}"));

        List<Comment> comment = catalogService.getMovieComments("1").collectList().block();

        assertNotNull(comment);
        assertEquals(1, comment.size());
        assertEquals(comment.get(0).getId(), "1");
        assertEquals(comment.get(0).getMovieId(), "1");
        assertEquals(comment.get(0).getContent(), "test");
    }

    private void prepareResponse(Consumer<MockResponse> consumer) {
        MockResponse response = new MockResponse();
        consumer.accept(response);
        this.server.enqueue(response);
    }
}