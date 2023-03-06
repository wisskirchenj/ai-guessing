package de.cofinpro.guessing.model;

/**
 * classes implementing the TextProcessor-interface process some user input and create a reply, that
 * depends on this input.
 */
public interface TextProcessor {

    /**
     * process (user) input and produce output depending on it.
     */
    String from(String input);
}
