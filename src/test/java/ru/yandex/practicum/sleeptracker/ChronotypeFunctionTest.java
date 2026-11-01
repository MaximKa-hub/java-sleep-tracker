package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeFunctionTest {
    private final ChronoTypeFunction function = new ChronoTypeFunction();

    @Test
    void shouldReturnDoveForEmptyList() {
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
    void shouldReturnDoveForNormalSleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 30), LocalDateTime.of(2025, 10, 2, 8, 0), QualitySleep.GOOD)
        );
        assertEquals(ChronoType.PIGEON, function.apply(sessions).getValue());
    }
}