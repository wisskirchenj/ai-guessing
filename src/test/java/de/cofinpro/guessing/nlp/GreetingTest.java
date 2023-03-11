package de.cofinpro.guessing.nlp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GreetingTest {

    static Stream<Arguments> greetingCorrespondsToDaytime() {
        return Stream.of(
                Arguments.of("Good morning", LocalTime.of(5, 0,0)),
                Arguments.of("Good morning", LocalTime.of(8, 29)),
                Arguments.of("Good morning", LocalTime.of(11, 59,59)),
                Arguments.of("Good afternoon", LocalTime.of(12, 0)),
                Arguments.of("Good afternoon", LocalTime.of(17, 59,59)),
                Arguments.of("Good evening", LocalTime.of(18, 0)),
                Arguments.of("Good evening", LocalTime.of(23, 59)),
                Arguments.of("Hi, Early Bird", LocalTime.of(3, 0)),
                Arguments.of("Hi, Early Bird", LocalTime.of(4, 59, 49)),
                Arguments.of("Hi, Night Owl", LocalTime.of(0, 0, 0)),
                Arguments.of("Hi, Night Owl", LocalTime.of(2, 59, 49))
        );
    }

    @ParameterizedTest
    @MethodSource
    void greetingCorrespondsToDaytime(String expectedGreeting, LocalTime daytime) {
        try (MockedStatic<LocalTime> daytimeMock = Mockito.mockStatic(LocalTime.class)) {
            daytimeMock.when(LocalTime::now).thenReturn(daytime);

            var greeting = new Greeting();
            assertEquals(expectedGreeting, greeting.text());
        }
    }
}