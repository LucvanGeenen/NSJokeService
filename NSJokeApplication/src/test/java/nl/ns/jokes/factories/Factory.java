package nl.ns.jokes.factories;

import nl.ns.jokes.adapters.model.JokesApiFlags;
import nl.ns.jokes.adapters.model.JokesApiJoke;
import nl.ns.jokes.model.Joke;
import nl.ns.jokes.model.JokeFlags;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Factory {

    public static JokesApiFlags createJokesApiFlags(){
        JokesApiFlags jokesApiFlags = new JokesApiFlags();
        jokesApiFlags.setNsfw(true);
        jokesApiFlags.setPolitical(false);
        jokesApiFlags.setReligious(true);
        jokesApiFlags.setExplicit(false);
        jokesApiFlags.setSexist(true);
        jokesApiFlags.setRacist(true);
        return jokesApiFlags;
    }

    public static JokeFlags createJokesFlags(){
        JokeFlags jokeFlags = new JokeFlags();
        jokeFlags.setNsfw(true);
        jokeFlags.setPolitical(false);
        jokeFlags.setReligious(true);
        jokeFlags.setExplicit(false);
        jokeFlags.setSexist(true);
        jokeFlags.setRacist(true);
        return jokeFlags;
    }

    public static JokesApiJoke createJokesApiJoke() {
        JokesApiFlags jokesApiFlags = createJokesApiFlags();
        JokesApiJoke jokesApiJoke = new JokesApiJoke(jokesApiFlags);

        jokesApiJoke.setId(99L);
        jokesApiJoke.setSafe(true);
        jokesApiJoke.setJoke("My awesome Joke");

        return jokesApiJoke;
    }

    public static List<Joke> createNotWantedJokes(){
        Joke racialJoke = new Joke();
        racialJoke.setSafe(true);
        racialJoke.setActualJoke("Racial Joke");
        racialJoke.getFlags().setRacist(true);

        Joke sexistJoke = new Joke();
        sexistJoke.setActualJoke("sexist Joke");
        sexistJoke.setSafe(true);
        sexistJoke.getFlags().setSexist(true);


        return new ArrayList<>(asList(racialJoke, sexistJoke));
    }

    public static List<Joke> createMixedJokes(){
        Joke racialJoke = new Joke();
        racialJoke.setSafe(true);
        racialJoke.setActualJoke("Racial Joke");
        racialJoke.getFlags().setRacist(true);

        Joke sexistJoke = new Joke();
        sexistJoke.setActualJoke("sexist Joke");
        sexistJoke.setSafe(true);
        sexistJoke.getFlags().setSexist(true);

        Joke religiousJoke = new Joke();
        religiousJoke.setActualJoke("Religious shortest Joke");
        religiousJoke.setSafe(true);
        religiousJoke.getFlags().setReligious(true);

        Joke religiousJoke2 = new Joke();
        religiousJoke2.setActualJoke("Religious a bit longer Joke");
        religiousJoke2.setSafe(true);
        religiousJoke2.getFlags().setReligious(true);

        Joke explicitJoke = new Joke();
        explicitJoke.setActualJoke("Explicit Joke");
        explicitJoke.setSafe(false);
        explicitJoke.getFlags().setReligious(true);

        return new ArrayList<>(asList(racialJoke, sexistJoke, religiousJoke, explicitJoke));
    }

}
