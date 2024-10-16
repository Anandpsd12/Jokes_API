package service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import model.Joke;


public class JokeServiceTest {

    private JokeService jokeService;

    @BeforeEach
    public void setup() {
        WebClient.Builder webClientBuilder = Mockito.mock(WebClient.Builder.class);
        WebClient webClient = Mockito.mock(WebClient.class);

        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        jokeService = new JokeService(webClientBuilder);
    }
    @Test
    public void testFetchJokes_successful() {
        Joke joke = new Joke("1", "general", "setup", "punchline");
        when(jokeService.fetchSingleJokeFromApi()).thenReturn(Mono.just(joke));
        Flux<Joke> jokeFlux = jokeService.fetchJokes(5);
        StepVerifier.create(jokeFlux).expectNextCount(5).verifyComplete();
    }
    @Test
    public void testFetchJokes_apiFailure() {
        when(jokeService.fetchSingleJokeFromApi())
                .thenReturn(Mono.error(new RuntimeException("Failed to fetch joke")));
                        Flux<Joke> jokeFlux = jokeService.fetchJokes(3);
                StepVerifier.create(jokeFlux)
                .verifyComplete(); // Expecting Flux to complete with no jokes(fallback behavior)
    }
}

