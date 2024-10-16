package exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;


@ControllerAdvice
public class GlobalErrorHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public Mono<ServerResponse> handleInvalidArgument(IllegalArgumentException e) {
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("Invalid request: " + e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ServerWebInputException.class)
    public Mono<ServerResponse>
    handleServerWebInputException(ServerWebInputException e) {
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("Invalid input: " + e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class
    )
    public Mono<ServerResponse> handleGeneralError(RuntimeException e) {
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("An error occurred: " + e.getMessage());
    }
}
