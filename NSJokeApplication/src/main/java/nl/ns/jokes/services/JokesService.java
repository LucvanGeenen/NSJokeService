package nl.ns.jokes.services;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ns.jokes.adapters.JokesApiAdapter;
import nl.ns.jokes.enums.BlacklistEnum;
import nl.ns.jokes.enums.CategoryEnum;
import nl.ns.jokes.model.Joke;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
@Slf4j
public class JokesService {

    private final JokesApiAdapter jokesApiAdapter;

    public Joke getShortestNonRacistNonSexistSafeJoke(){
        List<Joke> jokesFromApi = jokesApiAdapter.getJokesFromJokesApi();

        List<Joke> safeJokesFromApi = jokesFromApi.stream().filter(Joke::isSafe).toList();

        List<Joke> blackListedRemoved = removeBlacklisted(safeJokesFromApi, asList(BlacklistEnum.RACIST, BlacklistEnum.SEXIST));

        return blackListedRemoved.stream()
                .min(Comparator.comparing(joke -> joke.getActualJoke().length()))
                .orElse(new Joke());
    }

    public List<Joke> search(final CategoryEnum category, final int amount, final List<BlacklistEnum> blacklisted){
        List<Joke> jokesFromApi = jokesApiAdapter.getJokesFromJokesApi(category, amount, blacklisted);

        log.info("found {} jokes, start filtering jokes", jokesFromApi.size());

        List<Joke> actualJokes = jokesFromApi.stream()
                .filter(joke -> StringUtils.isNotBlank(joke.getActualJoke()))
                .toList();

        log.info("{} jokes were filtered out", jokesFromApi.size() - actualJokes.size());

        return actualJokes;
    }

    private List<Joke> removeBlacklisted(final List<Joke> jokes, final List<BlacklistEnum> blacklisted){
        return jokes.stream()
                .filter(joke -> blacklisted.stream().allMatch(listed -> listed.negate().test(joke)))
                .toList();
    }

}
