package de.cofinpro.guessing.model;

import java.util.List;
import java.util.Random;

/**
 * record producing some random Bye-message from some given options.
 * @param text message text.
 */
public record Bye(String text) {

    private static final Random RANDOM = new Random();
    private static final List<String> MESSAGES = List.of(
            "Have a nice day!",
            "See you soon!",
            "Thanks for playing!",
            "See you later.",
            "Hasta la vista baby.",
            "Bye!"
    );

    public Bye() {
        this(MESSAGES.get(RANDOM.nextInt(MESSAGES.size())));
    }
}
