package model;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Joke {
    private String id;
    private String type;
    private String setup;
    private String punchline;
}