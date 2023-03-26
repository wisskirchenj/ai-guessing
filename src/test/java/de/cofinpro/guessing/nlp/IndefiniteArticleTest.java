package de.cofinpro.guessing.nlp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IndefiniteArticleTest {

    static Stream<Arguments> whenNounGiven_ArticleAccordingSyntax() {
        return Stream.of(
                Arguments.of("a", "cat"),
                Arguments.of("a", "table"),
                Arguments.of("a", "human"),
                Arguments.of("an", "unicorn"),
                Arguments.of("an", "idle"),
                Arguments.of("an", "other"),
                Arguments.of("an", "essence"),
                Arguments.of("an", "apple")
        );
    }

    @ParameterizedTest
    @MethodSource
    void whenNounGiven_ArticleAccordingSyntax(String expectedArticle, String input) {
        var noun = Animal.from(input);
        assertNotNull(noun);
        assertEquals(expectedArticle, noun.nounWithIndefiniteArticle().split(" ")[0]);
    }

}