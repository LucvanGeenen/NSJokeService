package nl.ns.jokes.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;

public interface ExceptionsBaseEnum {

    HttpStatus getHttpStatus();

    URI getTypeAsUri();

    String getType();


}
