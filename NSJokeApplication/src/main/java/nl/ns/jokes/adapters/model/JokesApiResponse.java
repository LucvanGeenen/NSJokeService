package nl.ns.jokes.adapters.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class JokesApiResponse {

    private boolean error;
    private int amount;
    private List<JokesApiJoke> jokes = new ArrayList<>();

    private boolean internalError;
    private int code;
    private String message;
    private List<String> causedBy;
    private String additionalInfo;
    private Timestamp timestamp;


}
