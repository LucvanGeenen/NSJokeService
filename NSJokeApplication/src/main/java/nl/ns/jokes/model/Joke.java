package nl.ns.jokes.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.ns.jokes.adapters.model.JokesApiJoke;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Joke {
    private long id;
    private boolean safe;
    private String actualJoke;

    private JokeFlags flags = new JokeFlags();

    public boolean isNsfw(){
        return flags.isNsfw();
    }

    public boolean isReligious() {
        return flags.isReligious();
    }

    public boolean isPolitical(){
        return flags.isPolitical();
    }

    public boolean isRacist(){
        return flags.isRacist();
    }

    public boolean isSexist(){
        return flags.isSexist();
    }

    public boolean isExplicit() {
        return flags.isExplicit();
    }

    public Joke(final JokesApiJoke jokesApiJoke){
        this.id = jokesApiJoke.getId();
        this.safe = jokesApiJoke.isSafe();
        this.actualJoke = jokesApiJoke.getJoke();
        this.flags = jokesApiJoke.getFlags() != null ? new JokeFlags(jokesApiJoke.getFlags()) : null;
    }


}
