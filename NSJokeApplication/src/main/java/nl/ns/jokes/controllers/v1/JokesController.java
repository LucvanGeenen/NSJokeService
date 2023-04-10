package nl.ns.jokes.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ns.jokes.controllers.v1.model.JokeResponse;
import nl.ns.jokes.exception.RestCallErrorResponse;
import nl.ns.jokes.model.Joke;
import nl.ns.jokes.services.JokesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/jokes")
@RequiredArgsConstructor
@Slf4j
public class JokesController {

    private final JokesService jokesService;

    @GetMapping("/shortest-decent-joke")
    @Operation(
            summary = "This endpoint will return a single joke.",
            description = "This endpoint will return a single joke which is non sexist, non racial and safe to show and is also the shortest")
    @ApiResponse(responseCode = "200", description = "Joke found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = JokeResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestCallErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestCallErrorResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestCallErrorResponse.class)))
    public ResponseEntity<JokeResponse> getShortestNonRacistNonSexistSafeJoke() {

        log.info("Request received for retrieving the shortest, non racial, non sexist and safe joke");
        Joke joke = jokesService.getShortestNonRacistNonSexistSafeJoke();
        log.info(null != joke.getActualJoke() ? "A Joke was found" : "No joke was found");

        return ResponseEntity.ok(new JokeResponse(joke));
    }
}
