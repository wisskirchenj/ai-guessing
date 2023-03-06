package de.cofinpro.guessing.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class YesNoProcessorTest {

    private YesNoProcessor processor;

    @BeforeEach
    void setup() {
        processor = new YesNoProcessor();
    }

    static Stream<Arguments> whenRecognizableInput_parsedCorrectly() {
        return Stream.of(
                Arguments.of("y", "Yes"),
                Arguments.of("  yeah", "Yes"),
                Arguments.of("YEAH", "Yes"),
                Arguments.of(" yeah !", "Yes"),
                Arguments.of("you said it!", "Yes"),
                Arguments.of(" YOU bet.", "Yes"),
                Arguments.of("RIGHT!", "Yes"),
                Arguments.of("Affirmative.", "Yes"),
                Arguments.of("Indeed!", "Yes"),
                Arguments.of("  correct .", "Yes"),
                Arguments.of(" Sure.", "Yes"),
                Arguments.of(" YEP.", "Yes"),
                Arguments.of("Yes !", "Yes"),
                Arguments.of(" n !", "No"),
                Arguments.of("nah.", "No"),
                Arguments.of(" no Way", "No"),
                Arguments.of(" nOPE !", "No"),
                Arguments.of(" I don't think so!", "No"),
                Arguments.of("Negative", "No"),
                Arguments.of("Yeah no !", "No"),
                Arguments.of("no!", "No")
        );
    }

    @ParameterizedTest
    @MethodSource
    void whenRecognizableInput_parsedCorrectly(String input, String expectedAnswer) {
        assertEquals(expectedAnswer, processor.from(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"yes..", "No!!", "Yes sure", "Oops", ".no", "not sure"})
    void whenUnrecognizableInput_inputReturned(String input) {
        assertEquals(input, processor.from(input));
    }
}