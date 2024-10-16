package controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import service.JokeService;
import model.Joke;

@RestController
public class JokeController {
    private final JokeService jokeService;
    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }
    @GetMapping("/jokes")
    public Flux<Joke> getJokes(@RequestParam int count) {
        if (count <= 1 || count > 100) {
            throw new IllegalArgumentException("Count must be between 1 and 100");
        }
        return jokeService.fetchJokes(count);
    }
}

