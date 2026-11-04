package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxDurationFunctionTest {
    private final MaxDurationFunction function = new MaxDurationFunction();
    private final LocalDateTime base = LocalDateTime.of(2025, 10, 1, 0, 0);

    @Test
    void shouldReturnZeroForEmptyList() {
        assertEquals(0L, function.apply(Collections.emptyList()).getValue());
    }

    @Test
    void shouldReturnDurationForSingleSession() {
        List<SleepingSession> sessions = List.of(new SleepingSession(base, base.plusMinutes(480), QualitySleep.GOOD));
        assertEquals(480L, function.apply(sessions).getValue());
    }

    @Test
    void shouldReturnMaxFromMultipleSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusMinutes(300), QualitySleep.GOOD),
                new SleepingSession(base, base.plusMinutes(600), QualitySleep.NORMAL),
                new SleepingSession(base, base.plusMinutes(100), QualitySleep.BAD)
        );
        assertEquals(600L, function.apply(sessions).getValue());
    }

    @Test
    void shouldReturnMaxWhenAllDurationsEqual() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusMinutes(400), QualitySleep.GOOD),
                new SleepingSession(base, base.plusMinutes(400), QualitySleep.NORMAL)
        );
        assertEquals(400L, function.apply(sessions).getValue());
    }

    @Test
    void shouldHandleExtremelyLongSession() {
        List<SleepingSession> sessions = List.of(new SleepingSession(base, base.plusDays(2), QualitySleep.BAD));
        assertEquals(2880L, function.apply(sessions).getValue());
    }
}