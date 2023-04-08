package nl.ns.jokes.adapters;

import nl.ns.jokes.adapters.model.JokesApiFlags;
import nl.ns.jokes.adapters.model.JokesApiJoke;
import nl.ns.jokes.adapters.model.JokesApiResponse;
import nl.ns.jokes.factories.Factory;
import nl.ns.jokes.model.Joke;
import nl.ns.jokes.model.JokeFlags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static nl.ns.jokes.adapters.JokesApiAdapter.URI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JokesApiAdapterTest {

    @Mock private RestTemplate jokesApiRestTemplate;

    @InjectMocks
    private JokesApiAdapter jokesApiAdapter;

    @Test
    void getJokesFromJokesApi_empty_repsonse_body() {
        JokesApiResponse jokesApiResponse = null;

        when(jokesApiRestTemplate.getForEntity(String.format(URI, "single", 16), JokesApiResponse.class))
                .thenReturn(ResponseEntity.ok(jokesApiResponse));

        List<Joke> jokesFromApi = jokesApiAdapter.getJokesFromJokesApi();

        assertThat(jokesFromApi).isEmpty();
    }

    @Test
    void getJokesFromJokesApi_one_found_success() {
        JokesApiResponse jokesApiResponse = new JokesApiResponse();
        JokesApiJoke jokesApiJoke = Factory.createJokesApiJoke();
        jokesApiResponse.getJokes().add(jokesApiJoke);

        when(jokesApiRestTemplate.getForEntity(String.format(URI, "single", 16), JokesApiResponse.class))
                .thenReturn(ResponseEntity.ok(jokesApiResponse));

        List<Joke> jokesFromApi = jokesApiAdapter.getJokesFromJokesApi();

        assertThat(jokesFromApi).hasSize(1);
        assertJoke(jokesFromApi.get(0), jokesApiJoke);
    }

    @Test
    void getJokesFromJokesApi_multiple_found_success() {

        JokesApiResponse jokesApiResponse = new JokesApiResponse();
        JokesApiJoke jokesApiJoke1 = Factory.createJokesApiJoke();
        JokesApiJoke jokesApiJoke2 = Factory.createJokesApiJoke();

        List<JokesApiJoke> jokesApiJokes = asList(jokesApiJoke1, jokesApiJoke2);
        jokesApiResponse.getJokes().addAll(jokesApiJokes);

        when(jokesApiRestTemplate.getForEntity(String.format(URI, "single", 16), JokesApiResponse.class))
                .thenReturn(ResponseEntity.ok(jokesApiResponse));

        List<Joke> result = jokesApiAdapter.getJokesFromJokesApi();

        assertThat(result).hasSize(2);

        IntStream.range(0, result.size())
                .forEach(index -> assertJoke(result.get(index), jokesApiJokes.get(index)));
    }

        private void assertJoke(Joke actual, JokesApiJoke expected) {
        assertThat(actual.getJoke()).isEqualTo(expected.getJoke());
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.isSafe()).isEqualTo(expected.isSafe());

        assertFlags(actual.getFlags(), expected.getFlags());
    }

    private void assertFlags(JokeFlags actual, JokesApiFlags expected) {
        assertThat(actual.isNsfw()).isEqualTo(expected.isNsfw());
        assertThat(actual.isReligious()).isEqualTo(expected.isReligious());
        assertThat(actual.isPolitical()).isEqualTo(expected.isPolitical());
        assertThat(actual.isSexist()).isEqualTo(expected.isSexist());
        assertThat(actual.isRacist()).isEqualTo(expected.isRacist());
        assertThat(actual.isExplicit()).isEqualTo(expected.isExplicit());
    }

}
