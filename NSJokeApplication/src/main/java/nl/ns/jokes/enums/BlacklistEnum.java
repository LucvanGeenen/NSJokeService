package nl.ns.jokes.enums;

import nl.ns.jokes.model.Joke;

import java.util.function.Predicate;

public enum BlacklistEnum implements Predicate<Joke> {
    NSFW(Joke::isNsfw),
    RELIGIOUS(Joke::isReligious),
    POLITICAL(Joke::isPolitical),
    RACIST(Joke::isRacist),
    SEXIST(Joke::isSexist),
    EXPLICIT(Joke::isExplicit);

    private final Predicate<Joke> blackList;

    BlacklistEnum(final Predicate<Joke> predicate) {
        this.blackList = predicate;
    }

    @Override
    public boolean test(Joke joke) {
        return blackList.test(joke);
    }


}
