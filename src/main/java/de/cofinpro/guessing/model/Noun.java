package de.cofinpro.guessing.model;

import java.util.regex.Pattern;

/**
 * process a given noun from input with or without article.
 */
public record Noun(String text) {

    private static final Pattern NOUN_PATTERN = Pattern.compile("\\s*(an? |the )?(.*)");

    public String withDefiniteArticle() {
        return "The " + text;
    }

    public String withIndefiniteArticle() {
        return IndefiniteArticle.forNoun(text) + " " + text;
    }

    /**
     * factory method that processes a given noun from input with or without article.
     */
    public static Noun from(String userInput) {
        var animal = userInput.toLowerCase();
        var matcher = NOUN_PATTERN.matcher(animal);
        if (!matcher.matches()) throw new AssertionError(); // does not happen as anything matches on the regexp
        return new Noun(matcher.group(2));
    }
}
