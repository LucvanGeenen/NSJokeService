package nl.ns.jokes.controllers.v1;

import nl.ns.jokes.controllers.v1.model.JokeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class JokesControllerUITest extends UnitIntegrationTestBase {

    public final String URI = "/v1/jokes/shortest-decent-joke";

    @Test
    void searchDecentShortestJoke_found_shortest_decent_joke() {

        String uri = createUri(URI);
        ResponseEntity<JokeResponse> result = restTemplate.getForEntity(uri, JokeResponse.class);
        assertThat(result.getBody()).isNotNull();
        JokeResponse jokeResponse = result.getBody();
        assertThat(jokeResponse.getJoke()).isEqualTo("\"We messed up the keming again guys.\"");
        assertThat(jokeResponse.getId()).isEqualTo(20);
    }

}
