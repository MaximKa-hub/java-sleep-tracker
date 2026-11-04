package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronoTypeFunctionTest {
    private final ChronoTypeFunction function = new ChronoTypeFunction();

    @Test
    void shouldReturnPigeonForEmptyList() {
        assertEquals(ChronoType.PIGEON, function.apply(Collections.emptyList()).getValue());
    }

    @Test
    void shouldReturnOwlForLateSleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30), LocalDateTime.of(2025, 10, 2, 10, 0), QualitySleep.GOOD)
        );
        assertEquals(ChronoType.OWL, function.apply(sessions).getValue());
    }

    @Test
    void shouldReturnLarkForEarlySleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 21, 0), LocalDateTime.of(2025, 10, 2, 6, 0), QualitySleep.GOOD)
        );
        assertEquals(ChronoType.LARK, function.apply(sessions).getValue());
    }

    @Test
    void shouldReturnPigeonForNormalSleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 30), LocalDateTime.of(2025, 10, 2, 8, 0), QualitySleep.GOOD)
        );
        assertEquals(ChronoType.PIGEON, function.apply(sessions).getValue());
    }

    @Test
    void shouldReturnPigeonWhenTwoChronotypesHaveEqualCount() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30), LocalDateTime.of(2025, 10, 2, 10, 0), QualitySleep.GOOD), // OWL
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 21, 0), LocalDateTime.of(2025, 10, 3, 6, 0), QualitySleep.GOOD)   // LARK
        );
        assertEquals(ChronoType.PIGEON, function.apply(sessions).getValue());
    }

    @Test
    void shouldReturnPigeonWhenMaxIsSharedAmongThreeCategories() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30), LocalDateTime.of(2025, 10, 2, 10, 0), QualitySleep.GOOD), // OWL
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 30), LocalDateTime.of(2025, 10, 3, 10, 0), QualitySleep.GOOD), // OWL
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 21, 0), LocalDateTime.of(2025, 10, 4, 6, 0), QualitySleep.GOOD),  // LARK
                new SleepingSession(LocalDateTime.of(2025, 10, 4, 21, 0), LocalDateTime.of(2025, 10, 5, 6, 0), QualitySleep.GOOD),  // LARK
                new SleepingSession(LocalDateTime.of(2025, 10, 5, 22, 30), LocalDateTime.of(2025, 10, 6, 8, 0), QualitySleep.GOOD)  // PIGEON
        );
        assertEquals(ChronoType.PIGEON, function.apply(sessions).getValue());
    }

    @Test
    void shouldIgnoreDaytimeSleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 21, 0), LocalDateTime.of(2025, 10, 2, 6, 0), QualitySleep.GOOD),  // LARK
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 14, 0), LocalDateTime.of(2025, 10, 2, 16, 0), QualitySleep.GOOD) // Дневной сон
        );
        assertEquals(ChronoType.LARK, function.apply(sessions).getValue());
    }
}