package de.cofinpro.guessing.model;

import java.util.Set;

/**
 * (grammatically simplified) provider of an indefinite article to a given noun - just considering vocals.
 */
public class IndefiniteArticle implements TextProcessor {

    private static final Set<Character> VOCALS = Set.of('a', 'e', 'i', 'o', 'u');
    @Override
    public String from(String noun) {
        return VOCALS.contains(noun.charAt(0)) ? "an" : "a";
    }
}
