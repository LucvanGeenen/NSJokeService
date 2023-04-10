package nl.ns.jokes.adapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ns.jokes.adapters.model.JokesApiResponse;
import nl.ns.jokes.exception.NSExceptionsEnum;
import nl.ns.jokes.exception.NSServiceException;
import nl.ns.jokes.model.Joke;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JokesApiAdapter {

    protected static final String URI = "/joke/Any?type=%s&amount=%s";

    private final RestTemplate jokesApiRestTemplate;
    private final ObjectMapper objectMapper;

    public List<Joke> getJokesFromJokesApi() {
        return getExternalJokes("single", 16);
    }

    public List<Joke> getExternalJokes(final String type, final int amount) {
        try {
            ResponseEntity<JokesApiResponse> responseEntity = jokesApiRestTemplate.getForEntity(String.format(URI, type, amount), JokesApiResponse.class);
            if (null == responseEntity.getBody()) {
                return Collections.emptyList();
            }
            JokesApiResponse jokesApiResponse = responseEntity.getBody();
            if  (!jokesApiResponse.isError()){
                return jokesApiResponse.getJokes().stream().map(Joke::new).toList();
            }
            return handleErrorFunctional(jokesApiResponse);
        } catch (HttpClientErrorException hcee) {
            log.error("Client exception occurred : {} ", hcee.getLocalizedMessage(), hcee);
            throw new NSServiceException(NSExceptionsEnum.getNSExceptionsEnumByHttpStatusCode(hcee.getStatusCode()), hcee.getLocalizedMessage());
        } catch (RestClientException rce) {
            // At this point is should always be an 5xx error
            log.error("Calling joke api resulted in an error ", rce);
            throw new NSServiceException(NSExceptionsEnum.INTERNAL_SERVER_ERROR, rce.getLocalizedMessage());
        }
    }

    private List<Joke> handleErrorFunctional(final JokesApiResponse jokesApiResponse){
        log.error("Calling joke api resulted in an error, returning empty list");
        try {
            log.error(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jokesApiResponse));
            return Collections.emptyList();
        } catch (JsonProcessingException e) {
            throw new NSServiceException(NSExceptionsEnum.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
