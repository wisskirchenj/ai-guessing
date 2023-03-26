package de.cofinpro.guessing.nlp;

import de.cofinpro.guessing.io.ResourceProvider;

/**
 * Yes No answer record.
 */
public record YesNoAnswer(Choice text) {

    /**
     * Factory method to parse a given answer input into a YesNoAnswer record.
     * @param answer the answer input
     * @return Yes or No record if positively parsed or else null
     */
    public static YesNoAnswer from(String answer) {
        var answerText = answer.trim().toLowerCase();
        if (answerText.matches(ResourceProvider.INSTANCE.get("positiveAnswer.isCorrect"))) {
            return new YesNoAnswer(Choice.YES);
        }
        if (answerText.matches(ResourceProvider.INSTANCE.get("negativeAnswer.isCorrect"))) {
            return new YesNoAnswer(Choice.NO);
        }
        return null;
    }

    public enum Choice {
        YES,
        NO
    }
}
