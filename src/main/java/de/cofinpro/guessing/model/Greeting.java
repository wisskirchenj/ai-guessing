package de.cofinpro.guessing.model;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Map;

import static java.util.Map.Entry.comparingByKey;

/**
 * record producing a greeting adequate for the time of day.
 * @param text the greeting message.
 */
public record Greeting(String text) {

    private static final Map<Integer, String> GREETINGS_MAP = Map.of(
            18, "Good evening",
            12, "Good afternoon",
            5, "Good morning",
            3, "Hi, Early Bird",
            0, "Hi, Night Owl"
    );

    public Greeting() {
        this(getGreetingByDaytime(LocalTime.now()));
    }

    private static String getGreetingByDaytime(LocalTime now) {
        return GREETINGS_MAP.entrySet().stream()
                .sorted(comparingByKey(Comparator.reverseOrder()))
                .dropWhile(e -> now.getHour() < e.getKey())
                .findFirst()
                .map(Map.Entry::getValue).orElseThrow();
    }
}
