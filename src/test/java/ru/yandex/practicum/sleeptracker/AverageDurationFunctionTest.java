package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageDurationFunctionTest {
    private final AverageDurationFunction function = new AverageDurationFunction();
    private final LocalDateTime base = LocalDateTime.of(2025, 10, 1, 0, 0);

    @Test
    void shouldReturnZeroForEmptyList() {
        assertEquals(0.0, function.apply(Collections.emptyList()).getValue(), 0.001);
    }

    @Test
    void shouldReturnDurationForSingleSession() {
        List<SleepingSession> sessions = List.of(new SleepingSession(base, base.plusMinutes(300), QualitySleep.GOOD));
        assertEquals(300.0, function.apply(sessions).getValue(), 0.001);
    }

    @Test
    void shouldCalculateIntegerAverage() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusMinutes(200), QualitySleep.GOOD),
                new SleepingSession(base, base.plusMinutes(400), QualitySleep.GOOD)
        );
        assertEquals(300.0, function.apply(sessions).getValue(), 0.001);
    }

    @Test
    void shouldCalculateFractionalAverage() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusMinutes(100), QualitySleep.GOOD),
                new SleepingSession(base, base.plusMinutes(101), QualitySleep.GOOD)
        );
        assertEquals(100.5, function.apply(sessions).getValue(), 0.001);
    }
}