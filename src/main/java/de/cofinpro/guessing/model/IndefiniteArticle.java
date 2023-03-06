package de.cofinpro.guessing.model;

import java.util.Set;

/**
 * (grammatically simplified) provider util of an indefinite article to a given noun - just considering vocals.
 */
public class IndefiniteArticle {

    private IndefiniteArticle() {
        // no instances
    }

    private static final Set<Character> VOCALS = Set.of('a', 'e', 'i', 'o', 'u');

    public static String forNoun(String noun) {
        return VOCALS.contains(noun.charAt(0)) ? "an" : "a";
    }
}
