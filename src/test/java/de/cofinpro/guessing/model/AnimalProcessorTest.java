package de.cofinpro.guessing.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AnimalProcessorTest {

    private AnimalProcessor animalProcessor;

    @BeforeEach
    void setup() {
        animalProcessor = new AnimalProcessor();
    }

    static Stream<Arguments> animalProcessor_processesCorrectly() {
        return Stream.of(
                Arguments.of("a cat", "cat"),
                Arguments.of("a cat", "the cat"),
                Arguments.of("a cat", "a cat"),
                Arguments.of("an cat", "an cat"),
                Arguments.of("a eagle", "a eagle"),
                Arguments.of("an eagle", "eagle"),
                Arguments.of("an eagle", "the eagle"),
                Arguments.of("a green chicken", "green chicken"),
                Arguments.of("a green chicken", "the green chicken"),
                Arguments.of("an oval bug", "oval bug")
        );
    }

    @ParameterizedTest
    @MethodSource
    void animalProcessor_processesCorrectly(String expected, String input) {
        assertEquals(expected, animalProcessor.from(input));
    }

}