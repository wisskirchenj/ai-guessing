package de.cofinpro.guessing.nlp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DistinguishingFactTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "a cat", "does it swim?", "it swims"})
    void whenStatementInvalid_NullReturned(String input) {
        assertNull(DistinguishingFact.from(input, null, null));
    }

    @ParameterizedTest
    @CsvSource({
            "Is it a mammal?, It is a mammal",
            "Is it a mammal?, Is it a mammal?",
            "Does it have a horn?, It has a horn",
            "Does it have a horn?, does it have a horn",
            "Can it fly?, can it fly?",
            "Can it swim?, Can it swim",
    })
    void whenStatementValid_DistinguishingFactConstructedProperly(String expectedQuestion, String input) {
        var result = DistinguishingFact.from(input, null, null);
        assertNotNull(result);
        assertEquals(expectedQuestion, result.question());
    }

    @ParameterizedTest
    @CsvSource({
            "- The cat can't fly., - The bird can fly., It can fly, a cat, bird, true",
            "- The bird can fly., - The cat can't fly., can it fly?, a bird, cat, false",
            "- The bird has feathers., - The cat doesn't have feathers., It has feathers, a bird, cat, false",
            "- The bird isn't an insect., - The ant is an insect., Is it an insect., a bird, ant, true",
    })
    void whenStatementValidAndAnimals_LearningsCorrect(String expectedL1, String expectedL2, String input, String first,
                                                       String second, boolean trueForSecond) {
        var result = DistinguishingFact.from(input, Animal.from(first), Animal.from(second));
        assertNotNull(result);
        result.setTrueForSecondAnimal(trueForSecond);
        var lines = result.learnings().split("\n");
        assertEquals(expectedL1, lines[0].trim());
        assertEquals(expectedL2, lines[1].trim());
    }
}