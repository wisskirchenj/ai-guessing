package de.cofinpro.guessing.nlp;

import de.cofinpro.guessing.io.ResourceProvider;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Map;

import static java.util.Map.Entry.comparingByKey;

/**
 * record producing a i18n greeting adequate for the time of day.
 * @param text the greeting message.
 */
public record Greeting(String text) {

    private static final Map<Integer, String> GREETINGS_MAP = Map.of(
            18, "greeting.evening",
            12, "greeting.afternoon",
            5, "greeting.morning",
            3, "greeting.early",
            0, "greeting.night"
    );

    public Greeting() {
        this(getGreetingByDaytime(LocalTime.now()));
    }

    private static String getGreetingByDaytime(LocalTime now) {
        return GREETINGS_MAP.entrySet().stream()
                .sorted(comparingByKey(Comparator.reverseOrder()))
                .dropWhile(e -> now.getHour() < e.getKey())
                .findFirst()
                .map(Map.Entry::getValue)
                .map(ResourceProvider.INSTANCE::get).orElseThrow();
    }
}
