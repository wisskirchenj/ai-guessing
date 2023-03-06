package de.cofinpro.guessing.model;

/**
 * process a given animal input into a standard form with indefinite article.
 */
public class AnimalProcessor implements TextProcessor {

    /**
     * parses animal input given with or without article and returns it with a indefinite article standard form.
     */
    @Override
    public String from(String userInput) {
        var animal = userInput.toLowerCase();
        if (animal.matches("\\s*an? .*")) {
            return animal; // keep indefinite article from user
        }
        if (animal.startsWith("the ")) { // strip possible definite article
            animal = animal.substring(4);
        }
        return new IndefiniteArticle().from(animal) + " " + animal;
    }
}
