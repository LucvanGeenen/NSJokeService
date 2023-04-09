package nl.ns.jokes.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.ns.jokes.adapters.model.JokesApiFlags;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class JokeFlags {

    private boolean nsfw;
    private boolean political;
    private boolean religious;
    private boolean explicit;
    private boolean sexist;
    private boolean racist;

    public JokeFlags(final JokesApiFlags jokesApiFlags){
        this.nsfw = jokesApiFlags.isNsfw();
        this.political = jokesApiFlags.isPolitical();
        this.religious = jokesApiFlags.isReligious();
        this.explicit = jokesApiFlags.isExplicit();
        this.sexist = jokesApiFlags.isSexist();
        this.racist = jokesApiFlags.isRacist();

    }

}
