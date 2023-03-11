package de.cofinpro.guessing.nlp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class OrdinalTest {

    @Test
    void whenTwoInstancesCreated_flyweightCacheWorks() {
        var ordinal = Ordinal.of(2);
        var other = Ordinal.of(2);
        assertSame(ordinal, other);
    }

    @ParameterizedTest
    @CsvSource({
            "first, 1",
            "third, 3",
            "second, 2"
    })
    void whenOrdinalCreated_textIsCorrect(String expected, int index) {
        assertEquals(expected, Ordinal.of(index).text());
    }
}