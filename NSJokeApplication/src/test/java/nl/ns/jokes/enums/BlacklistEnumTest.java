package nl.ns.jokes.enums;

import nl.ns.jokes.factories.Factory;
import nl.ns.jokes.model.Joke;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BlacklistEnumTest {

    @Test
    void blackListCheck(){
        Joke joke = new Joke();
        joke.setFlags(Factory.createJokesFlags());

        joke.getFlags().setNsfw(false);
        assertThat(BlacklistEnum.NSFW.test(joke)).isFalse();
        joke.getFlags().setNsfw(true);
        assertThat(BlacklistEnum.NSFW.test(joke)).isTrue();

        joke.getFlags().setPolitical(false);
        assertThat(BlacklistEnum.POLITICAL.test(joke)).isFalse();
        joke.getFlags().setPolitical(true);
        assertThat(BlacklistEnum.POLITICAL.test(joke)).isTrue();

        joke.getFlags().setReligious(false);
        assertThat(BlacklistEnum.RELIGIOUS.test(joke)).isFalse();
        joke.getFlags().setReligious(true);
        assertThat(BlacklistEnum.RELIGIOUS.test(joke)).isTrue();

        joke.getFlags().setExplicit(false);
        assertThat(BlacklistEnum.EXPLICIT.test(joke)).isFalse();
        joke.getFlags().setExplicit(true);
        assertThat(BlacklistEnum.EXPLICIT.test(joke)).isTrue();

        joke.getFlags().setRacist(false);
        assertThat(BlacklistEnum.RACIST.test(joke)).isFalse();
        joke.getFlags().setRacist(true);
        assertThat(BlacklistEnum.RACIST.test(joke)).isTrue();

        joke.getFlags().setSexist(false);
        assertThat(BlacklistEnum.SEXIST.test(joke)).isFalse();
        joke.getFlags().setSexist(true);
        assertThat(BlacklistEnum.SEXIST.test(joke)).isTrue();

    }
}
