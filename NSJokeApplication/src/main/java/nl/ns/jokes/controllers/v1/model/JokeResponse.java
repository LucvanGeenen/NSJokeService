package nl.ns.jokes.controllers.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.ns.jokes.model.Joke;

@NoArgsConstructor
@Getter
@Setter
public class JokeResponse {

    private long id;
    private String joke;

    public JokeResponse(final Joke joke){
        this.id = joke.getId();
        this.joke = joke.getJoke();
    }

}
