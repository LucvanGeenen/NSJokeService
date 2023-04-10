package nl.ns.jokes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.net.URI;
import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum NSExceptionsEnum implements ExceptionsBaseEnum {

    BAD_REQUEST("/problem/bad-request", HttpStatus.BAD_REQUEST ),
    FORBIDDEN("/problem/forbidden", HttpStatus.FORBIDDEN ),
    NOT_FOUND("/problem/not-found", HttpStatus.NOT_FOUND ),
    INTERNAL_SERVER_ERROR("/problem/internal-server-error", HttpStatus.INTERNAL_SERVER_ERROR ),

    DEFAULT_EXCEPTION("/problem/unknown-error/im-a-tea-pot", HttpStatus.I_AM_A_TEAPOT);

    private final String type;
    private final HttpStatus httpStatus;

    @Override
    public URI getTypeAsUri() {return URI.create(this.type);}

    public static NSExceptionsEnum getNSExceptionsEnumByHttpStatusCode(final HttpStatusCode code) {
        return Arrays.stream(values())
                .filter(enumValues -> enumValues.httpStatus.value() == code.value())
                .findFirst()
                .orElse(DEFAULT_EXCEPTION);

    }

}
