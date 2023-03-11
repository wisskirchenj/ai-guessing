package de.cofinpro.guessing.nlp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ordinal representation, that consists of a number and its text ordinal representation. The flyweight pattern
 * is applied, so that (the few possible) instances are reused.
 * @param index number
 * @param text ordinal as text
 */
public record Ordinal(int index, String text) {

    private static final List<String> ORDINAL_TEXTS = List.of("first", "second", "third");
    // implement Flyweight
    private static final Map<Integer, Ordinal> ORDINALS = new HashMap<>();

    private Ordinal(int index) {
        this(index, ORDINAL_TEXTS.get(index - 1));
    }

    public static Ordinal of(int index) {
        return ORDINALS.computeIfAbsent(index, Ordinal::new);
    }
}
