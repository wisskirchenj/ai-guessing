package de.cofinpro.guessing.nlp;

import java.util.Set;

/**
 * Yes No answer record.
 */
public record YesNoAnswer(String text) {

    private static final Set<String> YES_OPTIONS = Set.of(
            "y", "yeah", "yes", "yep", "sure", "right", "affirmative", "correct", "indeed",
            "youbet", "exactly", "yousaidit"
    );
    private static final Set<String> NO_OPTIONS = Set.of(
            "n", "no", "noway", "nah", "nope", "negative", "idon'tthinkso", "yeahno"
    );

    /**
     * Factory metho to parse a given answer input into a YesNoAnswer record.
     * @param answer the answer input
     * @return Yes or No record if positively parsed or else null
     */
    public static YesNoAnswer from(String answer) {
        var answerText = answer.replace(" ", "").toLowerCase();
        answerText = answerText.matches(".*[.!]")
                ? answerText.substring(0, answerText.length() - 1)
                : answerText;
        if (YES_OPTIONS.contains(answerText)) {
            return new YesNoAnswer("Yes");
        }
        if (NO_OPTIONS.contains(answerText)) {
            return new YesNoAnswer("No");
        }
        return null;
    }
}
