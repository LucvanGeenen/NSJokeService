package nl.ns.jokes.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class NSServiceException extends RuntimeException {

    private final transient ExceptionsBaseEnum exceptionsEnum;

    private String logTrackCode;
    private transient List<StackErrorResponse> stackErrorResponses;

    public NSServiceException(ExceptionsBaseEnum exceptionsEnum, String message) {
        super(message);
        this.exceptionsEnum = exceptionsEnum;
    }

    /**
     * Not used but when a NSServiceException is thrown by another service, a new NSServiceException
     * can be created and use this method to add the previous exception to the errorResponse.
     * Doing that will give a complete stack of different exceptions from the different services;
     *
     * @param restCallErrorResponse
     * @return
     */
    public NSServiceException withErrorResponseStack(final RestCallErrorResponse restCallErrorResponse){
        this.logTrackCode = restCallErrorResponse.getLogTrackCode();
        this.stackErrorResponses = restCallErrorResponse.getStackErrorResponses();
        this.stackErrorResponses.add(new StackErrorResponse(restCallErrorResponse));
        return this;
    }

}
