package de.cofinpro.guessing.model;

import java.util.Set;

/**
 * Yes No answer parser and translator.
 */
public class YesNoProcessor implements TextProcessor {

    public static final Set<String> YES_NO_SET = Set.of("Yes", "No");
    private static final Set<String> YES_OPTIONS = Set.of(
            "y", "yeah", "yes", "yep", "sure", "right", "affirmative", "correct", "indeed",
            "youbet", "exactly", "yousaidit"
    );
    private static final Set<String> NO_OPTIONS = Set.of(
            "n", "no", "noway", "nah", "nope", "negative", "idon'tthinkso", "yeahno"
    );

    /**
     * Tries to parse the given answer input into Yes or No.
     * @param answer the answer input
     * @return Yes or No if positively parsed or else just returns the answer input given
     */
    @Override
    public String from(String answer) {
        var answerText = answer.replace(" ", "").toLowerCase();
        answerText = answerText.matches(".*[.!]")
                ? answerText.substring(0, answerText.length() - 1)
                : answerText;
        if (YES_OPTIONS.contains(answerText)) {
            return "Yes";
        }
        if (NO_OPTIONS.contains(answerText)) {
            return "No";
        }
        return answer;
    }
}
