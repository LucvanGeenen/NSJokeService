package nl.ns.jokes.model;

import nl.ns.jokes.adapters.model.JokesApiFlags;
import nl.ns.jokes.adapters.model.JokesApiJoke;
import nl.ns.jokes.factories.Factory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JokeTest {

    @Test
    void default_constructor(){
        Joke joke = new Joke();
        assertThat(joke.getFlags()).isNotNull();

        JokeFlags flags = joke.getFlags();
        assertThat(flags.isNsfw()).isFalse();
        assertThat(flags.isPolitical()).isFalse();
        assertThat(flags.isReligious()).isFalse();
        assertThat(flags.isExplicit()).isFalse();
        assertThat(flags.isSexist()).isFalse();
        assertThat(flags.isRacist()).isFalse();

        assertThat(joke.isSafe()).isFalse();
        assertThat(joke.isNsfw()).isFalse();
        assertThat(joke.isPolitical()).isFalse();
        assertThat(joke.isReligious()).isFalse();
        assertThat(joke.isExplicit()).isFalse();
        assertThat(joke.isSexist()).isFalse();
        assertThat(joke.isRacist()).isFalse();

    }

    @Test
    void custom_constructor(){
        JokesApiFlags jokesApiFlags = Factory.createJokesApiFlags();
        JokesApiJoke jokesApiJoke = new JokesApiJoke();
        jokesApiJoke.setSafe(true);
        jokesApiJoke.setId(999L);
        jokesApiJoke.setJoke("My awesome Joke");
        jokesApiJoke.setFlags(jokesApiFlags );

        Joke joke = new Joke(jokesApiJoke);
        assertThat(joke.getId()).isEqualTo(jokesApiJoke.getId());
        assertThat(joke.isSafe()).isEqualTo(jokesApiJoke.isSafe());
        assertThat(joke.getJoke()).isEqualTo(jokesApiJoke.getJoke());

        assertThat(joke.getFlags()).isNotNull();

        JokeFlags flags = joke.getFlags();

        assertThat(flags.isNsfw()).isEqualTo(jokesApiFlags.isNsfw());
        assertThat(flags.isPolitical()).isEqualTo(jokesApiFlags.isPolitical());
        assertThat(flags.isReligious()).isEqualTo(jokesApiFlags.isReligious());
        assertThat(flags.isExplicit()).isEqualTo(jokesApiFlags.isExplicit());
        assertThat(flags.isSexist()).isEqualTo(jokesApiFlags.isSexist());
        assertThat(flags.isRacist()).isEqualTo(jokesApiFlags.isRacist());

    }

}
