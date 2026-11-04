package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinDurationFunctionTest {
    private final MinDurationFunction function = new MinDurationFunction();
    private final LocalDateTime base = LocalDateTime.of(2025, 10, 1, 0, 0);

    @Test
    void shouldReturnZeroForEmptyList() {
        assertEquals(0L, function.apply(Collections.emptyList()).getValue());
    }

    @Test
    void shouldReturnDurationForSingleSession() {
        List<SleepingSession> sessions = List.of(new SleepingSession(base, base.plusMinutes(420), QualitySleep.GOOD));
        assertEquals(420L, function.apply(sessions).getValue());
    }

    @Test
    void shouldReturnMinFromMultipleSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusMinutes(400), QualitySleep.GOOD),
                new SleepingSession(base, base.plusMinutes(120), QualitySleep.BAD),
                new SleepingSession(base, base.plusMinutes(500), QualitySleep.NORMAL)
        );
        assertEquals(120L, function.apply(sessions).getValue());
    }
}