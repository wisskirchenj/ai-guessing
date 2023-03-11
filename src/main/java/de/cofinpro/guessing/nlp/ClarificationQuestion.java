package de.cofinpro.guessing.nlp;

import java.util.List;
import java.util.Random;

/**
 * record producing some random clarification question to a wrong Yes/No answer from some given options.
 * @param text question text.
 */
public record ClarificationQuestion(String text) {
    private static final Random RANDOM = new Random();
    private static final List<String> MESSAGES = List.of(
            "yes or no?",
            "I'm not sure I caught you: was it yes or no?",
            "Funny, I still don't understand, is it yes or no?",
            "Oh, it's too complicated for me: just tell me yes or no.",
            "Could you please simply say yes or no?",
            "Come on, yes or no?",
            "Oh, no, don't try to confuse me: say yes or no."
    );

    public ClarificationQuestion() {
        this(MESSAGES.get(RANDOM.nextInt(MESSAGES.size())));
    }
}
