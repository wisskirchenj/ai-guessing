package de.cofinpro.guessing.nlp;

import de.cofinpro.guessing.io.ResourceProvider;

/**
 * process a given noun from input with or without article.
 */
public record Animal(String nounWithIndefiniteArticle) implements QuestionProvider {

    public String withDefiniteArticle() {
        return ResourceProvider.INSTANCE.getReplaced("definite", nounWithIndefiniteArticle);
    }

    public String withIndefiniteArticle() {
        return nounWithIndefiniteArticle;
    }

    public String withoutArticle() {
        return ResourceProvider.INSTANCE.getReplaced("animalName", nounWithIndefiniteArticle);
    }

    @Override
    public String question() {
        return ResourceProvider.INSTANCE.getReplaced("guessAnimal", nounWithIndefiniteArticle);
    }

    @Override
    public String asStatement() {
        return ResourceProvider.INSTANCE.getReplaced("animalStatement", nounWithIndefiniteArticle);
    }

    /**
     * factory method that processes a given noun from input with or without article.
     */
    public static Animal from(String userInput) {
        if (!userInput.matches(ResourceProvider.INSTANCE.get("animal.isCorrect")))  {
            return null;
        }
        return new Animal(ResourceProvider.INSTANCE.getReplaced("animal", userInput));
    }
}
