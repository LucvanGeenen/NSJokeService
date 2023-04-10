package nl.ns.jokes.controllers.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Joke {
    private long id;
    private String actualJoke;

    public Joke(final nl.ns.jokes.model.Joke jokeModel){
        this.id = jokeModel.getId();
        this.actualJoke = jokeModel.getActualJoke();
    }
}
