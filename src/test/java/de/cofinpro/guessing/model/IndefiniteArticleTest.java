package de.cofinpro.guessing.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IndefiniteArticleTest {

    private IndefiniteArticle indefiniteArticle;

    @BeforeEach
    void setup() {
        indefiniteArticle = new IndefiniteArticle();
    }

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
    void whenNounGiven_ArticleAccordingSyntax(String expectedArticle, String noun) {
        assertEquals(expectedArticle, indefiniteArticle.from(noun));
    }

}