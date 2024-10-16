package controller;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import service.JokeService;
import model.Joke;

@SpringBootTest
@WebFluxTest(JokeController.class)
public class JokeControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private JokeService jokeService;
    @Test
    public void testGetJokes_validCount() {
        Joke joke = new Joke("1", "general", "setup", "punchline");
        Mockito.when(jokeService.fetchJokes(5)).thenReturn(Flux.just(joke, joke, joke, joke, joke));
        webTestClient.get().uri("/jokes?count=5")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Joke.class)
                .hasSize(5);
    }
    @Test
    public void testGetJokes_invalidCount() {
        webTestClient.get().uri("/jokes?count=150")
                .exchange()
                .expectStatus().isBadRequest();
    }
}
