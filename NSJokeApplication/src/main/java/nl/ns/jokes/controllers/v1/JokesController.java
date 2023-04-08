package nl.ns.jokes.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ns.jokes.controllers.v1.model.JokeResponse;
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
    public ResponseEntity<JokeResponse> getShortestNonRacistNonSexistSafeJoke() {
        Joke joke = jokesService.getShortestNonRacistNonSexistSafeJoke();
        return ResponseEntity.ok(new JokeResponse(joke));
    }


}
