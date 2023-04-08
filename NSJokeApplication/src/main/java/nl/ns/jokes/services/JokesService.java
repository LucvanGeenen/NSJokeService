package nl.ns.jokes.services;

import lombok.RequiredArgsConstructor;
import nl.ns.jokes.adapters.JokesApiAdapter;
import nl.ns.jokes.enums.BlacklistEnum;
import nl.ns.jokes.model.Joke;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class JokesService {

    private final JokesApiAdapter jokesApiAdapter;
    public Joke getShortestNonRacistNonSexistSafeJoke(){
        List<Joke> jokesFromApi = jokesApiAdapter.getJokesFromJokesApi();

        removeBlacklisted(jokesFromApi, asList(BlacklistEnum.RACIST, BlacklistEnum.SEXIST));

        jokesFromApi.removeIf(joke -> !joke.isSafe());

        return jokesFromApi.stream()
                .min(Comparator.comparing(joke -> joke.getJoke().length()))
                .orElse(new Joke());
    }

    private void removeBlacklisted(final List<Joke> jokes, final List<BlacklistEnum> blacklisted){
        blacklisted.forEach(jokes::removeIf);
    }

}
