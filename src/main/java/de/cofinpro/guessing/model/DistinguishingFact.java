package de.cofinpro.guessing.model;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.regex.Pattern;

/**
 * class that parses user input and models the distinguishing fact.
 */
@Setter
@Accessors(chain = true)
public class DistinguishingFact {

    private static final Pattern factPattern = Pattern.compile("it (is|has|can) (.*?)[.!?]?");

    private String auxiliaryVerb;
    private String attribute;
    private boolean trueForSecondAnimal;

    /**
     * creates and returns the template string for the fact with negation if appropriate. To decide,
     * which animal is negated it uses the trueForSecondAnimal field which is set on previous user question.
     * @param index array index: 0 for first and 1 for second animal.
     */
    public String templateForAnimal(int index) {
        return trueForSecondAnimal ^ index == 0 ? positiveTemplate() : negatedTemplate();
    }

    private String negatedTemplate() {
        return " - {} " + negated(auxiliaryVerb) + " " + attribute + ".";
    }

    private String positiveTemplate() {
        return " - {} " + auxiliaryVerb + " " + attribute + ".";
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
    public String question() {
        return " - " + switch (auxiliaryVerb) {
            case "is" -> "Is it ";
            case "has" -> "Does it have ";
            case "can" -> "Can it ";
            default -> "";
        } + attribute + "?\n";
    }

    /**
     * static factory method on parsed input.
     * @param input user input.
     * @return the DistinguishingFact with filled verb and attribute or null, if parsing fails.
     */
    public static DistinguishingFact from(String input) {
        var matcher = factPattern.matcher(input.trim().toLowerCase());
        if (!matcher.matches()) {
            return null;
        }
        return new DistinguishingFact()
                .setAuxiliaryVerb(matcher.group(1))
                .setAttribute(matcher.group(2));
    }
}
