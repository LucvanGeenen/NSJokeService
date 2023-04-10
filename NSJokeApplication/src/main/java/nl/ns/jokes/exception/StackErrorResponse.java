package nl.ns.jokes.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
@NoArgsConstructor
@Getter
@Setter
public class StackErrorResponse {

    private String thrownByService;
    private int httpStatusValue;
    private URI type;
    private String errorMessage;
    private String method;
    private String fullPath;
    private String queryString;
    private String logTrackCode;

    public StackErrorResponse(final RestCallErrorResponse restCallErrorResponse){
        this.thrownByService = restCallErrorResponse.getThrownByService();
        this.httpStatusValue = restCallErrorResponse.getHttpStatusValue();
        this.type = restCallErrorResponse.getType();
        this.errorMessage = restCallErrorResponse.getErrorMessage();
        this.method = restCallErrorResponse.getMethod();
        this.fullPath = restCallErrorResponse.getFullPath();
        this.queryString = restCallErrorResponse.getQueryString();
        this.logTrackCode = restCallErrorResponse.getLogTrackCode();
    }

}
