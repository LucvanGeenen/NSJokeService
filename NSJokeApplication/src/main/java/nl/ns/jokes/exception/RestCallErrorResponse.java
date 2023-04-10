package nl.ns.jokes.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@Slf4j
public class RestCallErrorResponse {
    private String thrownByService;
    private int httpStatusValue;
    private URI type;
    private String errorMessage;
    private String method;
    private String fullPath;
    private String queryString;
    private String logTrackCode;
    private List<StackErrorResponse> stackErrorResponses;

    /* for mapping with Jackson */
    public RestCallErrorResponse() {
    }

    public RestCallErrorResponse(final String thrownByService,
                                 final int httpStatusValue,
                                 final URI type,
                                 final String errorMessage,
                                 final String logTrackCode,
                                 final List<StackErrorResponse> stackErrorResponses,
                                 final HttpServletRequest request) {
        this.thrownByService = thrownByService;
        this.httpStatusValue = httpStatusValue;
        this.type = type;
        this.errorMessage = errorMessage;
        this.logTrackCode = logTrackCode == null ? UUID.randomUUID().toString() : logTrackCode;
        this.stackErrorResponses = CollectionUtils.isEmpty(stackErrorResponses) ? new ArrayList<>() : stackErrorResponses;
        this.method = (null == request) ? null : request.getMethod();
        this.fullPath = (null == request) ? null : request.getRequestURI();
        this.queryString = (null == request) ? null : request.getQueryString();
    }
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.warn("Pretty print jackson produced an error", e);
            return getClass().getName() + "@" + Integer.toHexString(hashCode());
        }
    }

}
