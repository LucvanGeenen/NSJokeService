package nl.ns.jokes.adapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ns.jokes.adapters.model.JokesApiResponse;
import nl.ns.jokes.enums.CategoryEnum;
import nl.ns.jokes.exception.NSExceptionsEnum;
import nl.ns.jokes.exception.NSServiceException;
import nl.ns.jokes.model.Joke;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JokesApiAdapter {

    private final RestTemplate jokesApiRestTemplate;
    private final ObjectMapper objectMapper;

    public List<Joke> getJokesFromJokesApi() {
        TreeMap<String, String > parameters = new TreeMap<>();
        parameters.put("type", "single");
        parameters.put("amount", "16");
        return getExternalJokes(createUri(CategoryEnum.ANY, parameters));
    }

    private List<Joke> getExternalJokes(final String uri) {
        try {
            ResponseEntity<JokesApiResponse> responseEntity = jokesApiRestTemplate.getForEntity(uri, JokesApiResponse.class);
            if (null == responseEntity.getBody()) {
                return Collections.emptyList();
            }
            JokesApiResponse jokesApiResponse = responseEntity.getBody();
            if (!jokesApiResponse.isError()) {
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


    private List<Joke> handleErrorFunctional(final JokesApiResponse jokesApiResponse) {
        log.error("Calling joke api resulted in an error, returning empty list");
        try {
            log.error(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jokesApiResponse));
            return Collections.emptyList();
        } catch (JsonProcessingException e) {
            throw new NSServiceException(NSExceptionsEnum.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    private String createUri(final CategoryEnum category, final Map<String, String> parameters) {
        final LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        parameters.forEach(params::add);

        return UriComponentsBuilder.newInstance()
                .path(category.getUrlParameterCategory())
                .queryParams(params)
                .build().toUriString();

    }
}
