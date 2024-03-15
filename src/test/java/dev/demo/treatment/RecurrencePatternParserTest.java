package dev.demo.treatment;

import dev.demo.treatment.service.RecurrencePatternParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class RecurrencePatternParserTest {
    private final RecurrencePatternParser parser = new RecurrencePatternParser();
    LocalDateTime currentTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 11, 0);


    @ParameterizedTest
    @ValueSource(strings = {"0 0 10 * * 5", "0 30 10 * * 5", "0 0 11 * * 5", "0 30 10 1,15 * 5", "0 30 10 3,15 * 5", "0 * * 15 * 5", "* 30 10 * * 5,6", "30 30 10 15 3 5"})
    @DisplayName("shouldGenerate method should return true")
    void testShouldGenerate(String  pattern) {
        assertTrue(parser.shouldGenerate(pattern, currentTime, endTime), "True should be returned for pattern: " + pattern);
    }

    @ParameterizedTest
    @ValueSource(strings = {"* * * * * *", "0 0 0 0 0 0", "0 0 10 * * 2-4,6", "0 0 0 0 0 0 0", "0 0 0 0 0"})
    @DisplayName("shouldGenerate method should throws exception")
    void exceptionShouldBeThrown(String pattern) {
        assertThrows(IllegalArgumentException.class, () -> parser.shouldGenerate(pattern, currentTime, endTime), "Exception should be thrown for pattern: " + pattern);
    }

    @Test
    @DisplayName("shouldGenerate method should throws exception for null")
    void exceptionShouldBeThrownForNull() {
        assertThrows(IllegalArgumentException.class, () -> parser.shouldGenerate(null, currentTime, endTime));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0 0 10 * * 6", "61 0 10 * * 6", "0 61 10 * * 6", "0 0 25 * * 6", "0 0 10 32 * 6", "0 0 10 * 13 6", "0 0 10 * * 8"})
    @DisplayName("shouldGenerate method should return false because of not valid values for time")
    void testShouldNotGenerateBecauseOfNotValidValues(String pattern) {
        assertFalse(parser.shouldGenerate(pattern, currentTime, endTime), "False should be returned for pattern: " + pattern);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0 1 11 * * 5", "59 59 9 * * 5", "0 30 10 16 * 5", "0 30 10 * 4 5", "0 30 10 15 * 6", "* 30 10 16 4 1"})
    @DisplayName("shouldGenerate method should return false because time from template  is not in range")
    void testShouldReturnFalse(String pattern) {
        assertFalse(parser.shouldGenerate(pattern, currentTime, endTime));
    }

}