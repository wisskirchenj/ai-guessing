package de.cofinpro.guessing.nlp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class YesNoAnswerTest {

    static Stream<Arguments> whenRecognizableInput_parsedCorrectly() {
        return Stream.of(
                Arguments.of("y", "YES"),
                Arguments.of("  yeah", "YES"),
                Arguments.of("YEAH", "YES"),
                Arguments.of(" yeah!", "YES"),
                Arguments.of("you said it!", "YES"),
                Arguments.of(" YOU bet.", "YES"),
                Arguments.of("RIGHT!", "YES"),
                Arguments.of("Affirmative.", "YES"),
                Arguments.of("Indeed!", "YES"),
                Arguments.of("  correct. ", "YES"),
                Arguments.of(" Sure.", "YES"),
                Arguments.of(" YEP.", "YES"),
                Arguments.of("Yes!", "YES"),
                Arguments.of(" n!", "NO"),
                Arguments.of("nah.", "NO"),
                Arguments.of(" no Way", "NO"),
                Arguments.of(" nOPE!", "NO"),
                Arguments.of(" I don't think so!", "NO"),
                Arguments.of("Negative", "NO"),
                Arguments.of("Yeah no!", "NO"),
                Arguments.of("no!", "NO")
        );
    }

    @ParameterizedTest
    @MethodSource
    void whenRecognizableInput_parsedCorrectly(String input, String expectedAnswer) {
        var processor = YesNoAnswer.from(input);
        assertNotNull(processor);
        assertEquals(expectedAnswer, processor.text().name());
    }

    @ParameterizedTest
    @ValueSource(strings = {"yes..", "No!!", "Yes sure", "Oops", ".no", "not sure"})
    void whenUnrecognizableInput_inputReturned(String input) {
        assertNull(YesNoAnswer.from(input));
    }
}