package nl.ns.jokes.adapters.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JokesApiFlags {

    private boolean nsfw;
    private boolean political;
    private boolean religious;
    private boolean explicit;
    private boolean sexist;
    private boolean racist;

}
