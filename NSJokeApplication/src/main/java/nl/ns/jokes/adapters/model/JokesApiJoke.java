package nl.ns.jokes.adapters.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class JokesApiJoke {

    private long id;
    private String joke;
    private boolean safe;
    private JokesApiFlags flags = new JokesApiFlags();

    public JokesApiJoke(final JokesApiFlags jokesApiFlags){
        this.flags = jokesApiFlags;
    }
}
