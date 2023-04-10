package nl.ns.jokes.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class NSControllerExceptionAdvice {

    @Value("${service.name}")
    private String serviceName;
    @ApiResponse(
            description = "The API call failed. Reference the response body for specifics.",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RestCallErrorResponse.class))})
    @ExceptionHandler(NSServiceException.class)
    public ResponseEntity<RestCallErrorResponse> handleNSServiceException(
            final HttpServletRequest request,
            final NSServiceException exception) {
        return createRestCallErrorResponse(request, exception.getLogTrackCode(), exception.getExceptionsEnum(),
                exception.getStackErrorResponses(), exception.getLocalizedMessage());
    }

    private ResponseEntity<RestCallErrorResponse> createRestCallErrorResponse(
            final HttpServletRequest request,
            final String logTrackCode,
            final ExceptionsBaseEnum exceptionsEnum,
            final List<StackErrorResponse> stackErrorResponses,
            final String message) {

        final RestCallErrorResponse restCallErrorResponse =
                new RestCallErrorResponse(
                        serviceName,
                        exceptionsEnum.getHttpStatus().value(),
                        exceptionsEnum.getTypeAsUri(),
                        message,
                        logTrackCode,
                        stackErrorResponses,
                        request);

        log.error("Rest call error response created : {}", restCallErrorResponse);

        return ResponseEntity
                .status(exceptionsEnum.getHttpStatus())
                .body(restCallErrorResponse);
    }


}
