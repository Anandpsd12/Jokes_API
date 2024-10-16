package service;

import model.Joke;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JokeService {

    private final Logger logger = LoggerFactory.getLogger(JokeService.class);

    // Injected WebClient
    private final WebClient webClient;

    // Constructor to inject WebClient.Builder
    public JokeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    // Method to fetch multiple jokes
    public Flux<Joke> fetchJokes(int count) {
        return Flux.range(1, count)
                .flatMap(i -> fetchSingleJokeFromApi()) // Fetch each joke
                .onErrorResume(e -> {
                    logger.error("Error occurred while fetching jokes: {}", e.getMessage());
                    return Flux.empty(); // Return empty Flux on error
                });
    }

    // Method to fetch a single joke from the external API
    Mono<Joke> fetchSingleJokeFromApi() {
        String jokesApiUrl = "https://official-joke-api.appspot.com/random_joke"; // External joke API URL
        return webClient.get().uri(jokesApiUrl).retrieve().onStatus(status -> !status.is2xxSuccessful(), clientResponse -> {
                    logger.error("Failed to fetch joke from the API, status: {}", clientResponse.statusCode());
                    return Mono.error(new RuntimeException("Failed to fetch joke from the public API"));
                })
                .bodyToMono(Joke.class) // Convert response to Joke object
                .onErrorResume(e -> {
                    logger.error("Error while fetching joke: {}", e.getMessage());
                    return Mono.empty(); // Return an empty Mono on error
                });
    }
}
