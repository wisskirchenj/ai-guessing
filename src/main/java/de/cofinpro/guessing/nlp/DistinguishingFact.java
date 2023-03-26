package de.cofinpro.guessing.nlp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.cofinpro.guessing.io.ResourceProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static de.cofinpro.guessing.io.ResourceProvider.capitalize;

/**
 * class that parses a user given distinguishing statement for two animals (stored as Animal) and models their
 * distinguishing fact.
 */
@Setter
@Getter
@Accessors(chain = true)
public class DistinguishingFact implements QuestionProvider {

    @JsonIgnore
    private final Animal[] animals = new Animal[2];
    private String fact;
    @JsonIgnore
    private boolean trueForSecondAnimal;

    private DistinguishingFact setAnimals(Animal firstAnimal, Animal secondAnimal) {
        animals[0] = firstAnimal;
        animals[1] = secondAnimal;
        return this;
    }

    private String negatedStatementFor(Animal animal) {
        return " - " + capitalize(ResourceProvider.INSTANCE
                .getReplaced("animalFact", negated()).formatted(animal.withDefiniteArticle())) + ".";
    }

    private String positiveStatementFor(Animal animal) {
        return " - " + capitalize(ResourceProvider.INSTANCE
                .getReplaced("animalFact", fact).formatted(animal.withDefiniteArticle())) + ".";
    }

    private String negated() {
        return ResourceProvider.INSTANCE.getReplaced("negative", fact);
    }

    /**
     * create the distinguishing question formulation.
     */
    @Override
    public String question() {
        return capitalize(ResourceProvider.INSTANCE.getReplaced("question", fact));
    }

    /**
     * return a string representation of the learnings with this distinguishing statement.
     */
    public String learnings() {
        if (trueForSecondAnimal) {
            return "%s%n%s".formatted(negatedStatementFor(animals[0]), positiveStatementFor(animals[1]));
        }
        return "%s%n%s".formatted(positiveStatementFor(animals[0]), negatedStatementFor(animals[1]));
    }

    /**
     * static factory method on parsed input.
     * @param input user input
     * @param firstAnimal first animal for this distinguishing fact
     * @param secondAnimal second animal for this distinguishing fact
     * @return the DistinguishingFact with filled fact field to persist - or null, if parsing fails.
     */
    public static DistinguishingFact from(String input, Animal firstAnimal, Animal secondAnimal) {
        var fact = input.trim();
        if (!fact.matches(ResourceProvider.INSTANCE.get("statement.isCorrect")))  {
            return null;
        }
        return new DistinguishingFact()
                .setFact(ResourceProvider.INSTANCE.getReplaced("statement", fact))
                .setAnimals(firstAnimal, secondAnimal);
    }

    @Override
    public String asStatement() {
        return capitalize(fact);
    }

    public String asNegativeStatement() {
        return capitalize(negated());
    }
}
