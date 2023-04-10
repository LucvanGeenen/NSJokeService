package nl.ns.jokes.adapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import nl.ns.jokes.adapters.model.JokesApiFlags;
import nl.ns.jokes.adapters.model.JokesApiJoke;
import nl.ns.jokes.adapters.model.JokesApiResponse;
import nl.ns.jokes.exception.NSExceptionsEnum;
import nl.ns.jokes.exception.NSServiceException;
import nl.ns.jokes.factories.Factory;
import nl.ns.jokes.model.Joke;
import nl.ns.jokes.model.JokeFlags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JokesApiAdapterTest {

    private static final String URI = "/Any?amount=16&type=single";

    @Mock private RestTemplate jokesApiRestTemplate;
    @Mock private ObjectMapper objectMapper;

    @InjectMocks
    private JokesApiAdapter jokesApiAdapter;

    @Mock private ObjectWriter objectWriter;

    @Test
    void getJokesFromJokesApi_empty_repsonse_body() {
        JokesApiResponse jokesApiResponse = null;

        when(jokesApiRestTemplate.getForEntity(URI, JokesApiResponse.class))
                .thenReturn(ResponseEntity.ok(jokesApiResponse));

        List<Joke> jokesFromApi = jokesApiAdapter.getJokesFromJokesApi();

        assertThat(jokesFromApi).isEmpty();
    }

    @Test
    void getJokesFromJokesApi_repsonse_body() throws JsonProcessingException {
        JokesApiResponse jokesApiResponse = new JokesApiResponse();
        jokesApiResponse.setError(true);

        when(objectMapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
        when(jokesApiRestTemplate.getForEntity(URI, JokesApiResponse.class))
                .thenReturn(ResponseEntity.ok(jokesApiResponse));

        List<Joke> jokesFromApi = jokesApiAdapter.getJokesFromJokesApi();

        assertThat(jokesFromApi).isEmpty();
        verify(objectMapper).writerWithDefaultPrettyPrinter();
        verify(objectWriter).writeValueAsString(jokesApiResponse);
    }

    @Test
    void getJokesFromJokesApi_one_found_success() {
        JokesApiResponse jokesApiResponse = new JokesApiResponse();
        JokesApiJoke jokesApiJoke = Factory.createJokesApiJoke();
        jokesApiResponse.getJokes().add(jokesApiJoke);

        when(jokesApiRestTemplate.getForEntity(URI, JokesApiResponse.class))
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

        when(jokesApiRestTemplate.getForEntity(URI, JokesApiResponse.class))
                .thenReturn(ResponseEntity.ok(jokesApiResponse));

        List<Joke> result = jokesApiAdapter.getJokesFromJokesApi();

        assertThat(result).hasSize(2);

        IntStream.range(0, result.size())
                .forEach(index -> assertJoke(result.get(index), jokesApiJokes.get(index)));
    }

    @Test
    void getJokesFromJokesApi_api_throws_restclientexception(){
        HttpServerErrorException error =
                HttpServerErrorException.create(HttpStatusCode.valueOf(500), "myMessage", new HttpHeaders(), null, null);

        doThrow(error).when(jokesApiRestTemplate).getForEntity(URI, JokesApiResponse.class);

        NSServiceException nsse = assertThrows(NSServiceException.class, () -> jokesApiAdapter.getJokesFromJokesApi());

        assertThat(nsse.getLocalizedMessage()).isEqualTo("500 myMessage");
        assertThat(nsse.getExceptionsEnum()).isEqualTo(NSExceptionsEnum.INTERNAL_SERVER_ERROR);
    }

    @Test
    void getJokesFromJokesApi_api_throws_httpclienterroe(){
        HttpClientErrorException error =
                HttpClientErrorException.create(HttpStatusCode.valueOf(400), "Bad request", new HttpHeaders(), null, null);

        doThrow(error).when(jokesApiRestTemplate).getForEntity(URI, JokesApiResponse.class);

        NSServiceException nsse = assertThrows(NSServiceException.class, () -> jokesApiAdapter.getJokesFromJokesApi());

        assertThat(nsse.getLocalizedMessage()).isEqualTo("400 Bad request");
        assertThat(nsse.getExceptionsEnum()).isEqualTo(NSExceptionsEnum.BAD_REQUEST);
    }

    private void assertJoke(Joke actual, JokesApiJoke expected) {
        assertThat(actual.getActualJoke()).isEqualTo(expected.getJoke());
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
