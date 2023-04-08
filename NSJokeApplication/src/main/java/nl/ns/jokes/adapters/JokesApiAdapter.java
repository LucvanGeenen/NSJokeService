package nl.ns.jokes.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ns.jokes.adapters.model.JokesApiJoke;
import nl.ns.jokes.adapters.model.JokesApiResponse;
import nl.ns.jokes.model.Joke;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JokesApiAdapter {

    protected static final String URI = "/joke/Any?type=%s&amount=%s";

    private final RestTemplate jokesApiRestTemplate;

    public List<Joke> getJokesFromJokesApi() {
        return getExternalJokes("single", 16);
    }

    public List<Joke> getExternalJokes(final String type, final int amount) {
        try {
            ResponseEntity<JokesApiResponse> response = jokesApiRestTemplate.getForEntity(String.format(URI, type, amount), JokesApiResponse.class);
            if (null == response.getBody()) {
                return Collections.emptyList();
            }

            List<JokesApiJoke> externalJokes = response.getBody().getJokes();
            return externalJokes.stream().map(Joke::new).collect(Collectors.toList());
        } catch (HttpClientErrorException hcee) {
            log.error("Client exception occurred : {} ", hcee.getLocalizedMessage(), hcee);
            throw new IllegalArgumentException();
        } catch (RestClientException rce) {
            // At this point is should always be an 5xx error
            log.error("Calling joke api resulted in an error ", rce);
            throw new IllegalArgumentException();
        }
    }

}
