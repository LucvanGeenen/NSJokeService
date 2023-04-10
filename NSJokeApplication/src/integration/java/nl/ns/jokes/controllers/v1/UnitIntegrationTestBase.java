package nl.ns.jokes.controllers.v1;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class UnitIntegrationTestBase {

    @LocalServerPort
    private String serverPort;

    @Value("${wiremock.port:8001}")
    private int wireMockPort;

    private WireMockServer wireMockServer;

    public final TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeAll
    public void setup() {
        wireMockServer = new WireMockServer(
                new WireMockConfiguration()
                        .usingFilesUnderClasspath("wiremock")
                        .port(wireMockPort));
        // For verifying stub calls
        configureFor("localhost", wireMockPort);
        wireMockServer.start();
    }

    @BeforeEach
    public void setupForTest() {
            wireMockServer.resetRequests();
    }

    @AfterAll
    public void tearDown() {
            wireMockServer.stop();
    }

    public String createUri(String path, Object... pathVariables) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(serverPort)
                .path(String.format(path, pathVariables))
                .build().toUriString();

    }

}
