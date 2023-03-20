package de.cofinpro.guessing.nlp;

import java.util.regex.Pattern;

/**
 * process a given noun from input with or without article.
 */
public record Noun(String text, String indefiniteArticle) implements QuestionProvider {

    private static final Pattern NOUN_PATTERN = Pattern.compile("\\s*(an? |the )?(.*)");

    public String withDefiniteArticle() {
        return "The " + text;
    }

    public String withIndefiniteArticle() {
        return indefiniteArticle + " " + text;
    }

    @Override
    public String question() {
        return "Is it %s?".formatted(withIndefiniteArticle());
    }

    @Override
    public String asStatement() {
        return "It is %s.".formatted(withIndefiniteArticle());
    }

    /**
     * factory method that processes a given noun from input with or without article.
     */
    public static Noun from(String userInput) {
        var animal = userInput.toLowerCase();
        var matcher = NOUN_PATTERN.matcher(animal);
        if (!matcher.matches())  {
            throw new AssertionError(); // does not happen as anything matches on the regexp
        }
        var givenArticle = matcher.group(1);
        var noun = matcher.group(2);
        var article = givenArticle != null && givenArticle.startsWith("a")
                ? givenArticle.trim() : IndefiniteArticle.forNoun(noun);
        return new Noun(noun, article);
    }
}
