package de.cofinpro.guessing.nlp;

import java.util.List;
import java.util.Random;

/**
 * record producing some random joy expression as "Wow" from some fixed options.
 * @param text message text.
 */
public record JoyExpression(String text) {

    private static final Random RANDOM = new Random();
    private static final List<String> MESSAGES = List.of(
            "Great!",
            "Cool!",
            "Wow!",
            "Super.",
            "Awesome.",
            "Perfect!",
            "Nice!",
            "Wonderful!",
            "Yeah!"
    );

    public JoyExpression() {
        this(MESSAGES.get(RANDOM.nextInt(MESSAGES.size())));
    }
}
