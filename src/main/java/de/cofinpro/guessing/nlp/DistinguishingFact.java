package de.cofinpro.guessing.nlp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.regex.Pattern;

/**
 * class that parses a user given distinguishing statement for two animals (stored as Noun) and models their
 * distinguishing fact.
 */
@Setter
@Getter
@Accessors(chain = true)
public class DistinguishingFact implements QuestionProvider {

    private static final Pattern factPattern = Pattern.compile("it (is|has|can) (.*?)[.!?]?");

    @JsonIgnore
    private final Noun[] animals = new Noun[2];
    private String auxiliaryVerb;
    private String attribute;
    @JsonIgnore
    private boolean trueForSecondAnimal;

    private DistinguishingFact setAnimals(Noun firstAnimal, Noun secondAnimal) {
        animals[0] = firstAnimal;
        animals[1] = secondAnimal;
        return this;
    }

    private String negatedStatementFor(Noun animal) {
        return " - %s %s %s.".formatted(animal.withDefiniteArticle(), negated(auxiliaryVerb), attribute);
    }

    private String positiveStatementFor(Noun animal) {
        return " - %s %s %s.".formatted(animal.withDefiniteArticle(), auxiliaryVerb, attribute);
    }

    private String negated(String auxiliaryVerb) {
        return switch (auxiliaryVerb) {
            case "is" -> "isn't";
            case "has" -> "doesn't have";
            case "can" -> "can't";
            default -> "";
        };
    }

    /**
     * create the distinguishing question formulation.
     */
    @Override
    public String question() {
        return switch (auxiliaryVerb) {
            case "is" -> "Is it ";
            case "has" -> "Does it have ";
            case "can" -> "Can it ";
            default -> "";
        } + attribute + "?";
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
     * @return the DistinguishingFact with filled verb, attribute and animals field - or null, if parsing fails.
     */
    public static DistinguishingFact from(String input, Noun firstAnimal, Noun secondAnimal) {
        var matcher = factPattern.matcher(input.trim().toLowerCase());
        if (!matcher.matches()) {
            return null;
        }
        return new DistinguishingFact()
                .setAuxiliaryVerb(matcher.group(1))
                .setAttribute(matcher.group(2))
                .setAnimals(firstAnimal, secondAnimal);
    }

    @Override
    public String asStatement() {
        return "It %s %s.".formatted(auxiliaryVerb, attribute);
    }

    public String asNegativeStatement() {
        return "It %s %s.".formatted(negated(auxiliaryVerb), attribute);
    }
}
