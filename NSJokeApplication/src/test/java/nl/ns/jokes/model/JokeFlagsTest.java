package nl.ns.jokes.model;

import nl.ns.jokes.adapters.model.JokesApiFlags;
import nl.ns.jokes.factories.Factory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JokeFlagsTest {

    @Test
    void flagMapper(){
        JokesApiFlags jokesApiFlags = Factory.createJokesApiFlags();
        JokeFlags jokeFlags = new JokeFlags(jokesApiFlags);

        assertThat(jokeFlags.isNsfw()).isEqualTo(jokesApiFlags.isNsfw());
        assertThat(jokeFlags.isPolitical()).isEqualTo(jokesApiFlags.isPolitical());
        assertThat(jokeFlags.isReligious()).isEqualTo(jokesApiFlags.isReligious());
        assertThat(jokeFlags.isExplicit()).isEqualTo(jokesApiFlags.isExplicit());
        assertThat(jokeFlags.isSexist()).isEqualTo(jokesApiFlags.isSexist());
        assertThat(jokeFlags.isRacist()).isEqualTo(jokesApiFlags.isRacist());
    }
}
