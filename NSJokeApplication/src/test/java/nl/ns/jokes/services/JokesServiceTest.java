package nl.ns.jokes.services;

import nl.ns.jokes.adapters.JokesApiAdapter;
import nl.ns.jokes.model.Joke;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static nl.ns.jokes.factories.Factory.createMixedJokes;
import static nl.ns.jokes.factories.Factory.createNotWantedJokes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JokesServiceTest {

    @Mock private JokesApiAdapter jokesApiAdapter;

    @InjectMocks
    private JokesService jokesService;

    @Test
    void getShortestNonRacistNonSexistSafeJoke_mix_jokes_success(){
        List<Joke> jokes = createMixedJokes();
        when(jokesApiAdapter.getJokesFromJokesApi()).thenReturn(jokes);

        Joke foundJoke = jokesService.getShortestNonRacistNonSexistSafeJoke();

        assertThat(foundJoke.getActualJoke()).isEqualTo("Religious shortest Joke");
        assertThat(foundJoke.isReligious()).isTrue();
    }

    @Test
    void getShortestNonRacistNonSexistSafeJoke_only_not_wanted_jokes(){
        List<Joke> jokes = createNotWantedJokes();
        when(jokesApiAdapter.getJokesFromJokesApi()).thenReturn(jokes);

        Joke foundJoke = jokesService.getShortestNonRacistNonSexistSafeJoke();
        assertThat(foundJoke.getActualJoke()).isNull();
        assertThat(foundJoke.getId()).isZero();

    }

}
