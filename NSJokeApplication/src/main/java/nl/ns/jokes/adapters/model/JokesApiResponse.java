package nl.ns.jokes.adapters.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class JokesApiResponse {

    private boolean error;
    private int amount;
    private List<JokesApiJoke> jokes = new ArrayList<>();
}
