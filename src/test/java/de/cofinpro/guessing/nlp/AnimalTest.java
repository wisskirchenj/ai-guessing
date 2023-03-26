package de.cofinpro.guessing.nlp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    static Stream<Arguments> animalProcessor_processesCorrectly() {
        return Stream.of(
                Arguments.of("a cat", "cat"),
                Arguments.of("a cat", "the cat"),
                Arguments.of("a cat", "a cat"),
                Arguments.of("an eagle", "eagle"),
                Arguments.of("an eagle", "the eagle"),
                Arguments.of("a green chicken", "green chicken"),
                Arguments.of("a green chicken", "the green chicken"),
                Arguments.of("an oval bug", "oval bug"),
                Arguments.of(null, "a"),
                Arguments.of(null, "")
        );
    }

    @ParameterizedTest
    @MethodSource
    void animalProcessor_processesCorrectly(String expected, String input) {
        var animal = Animal.from(input);
        if (expected == null) {
            assertNull(animal);
            return;
        }
        assertNotNull(animal);
        assertEquals(expected, animal.withIndefiniteArticle());
    }

}