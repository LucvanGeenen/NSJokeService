package nl.ns.jokes.controllers.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class JokeListResponse {

    private List<Joke> jokes;

    public JokeListResponse(final List<Joke> jokes){
        this.jokes = jokes;
    }

}
