package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightsFunctionTest {
    private final SleeplessNightsFunction function = new SleeplessNightsFunction();

    @Test
    void shouldReturnZeroForEmptyList() {
        assertEquals(0L, function.apply(Collections.emptyList()).getValue());
    }

    @Test
    void shouldReturnZeroWhenSleptEveryNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0), LocalDateTime.of(2025, 10, 2, 7, 0), QualitySleep.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 0), LocalDateTime.of(2025, 10, 3, 7, 0), QualitySleep.GOOD)
        );
        assertEquals(0L, function.apply(sessions).getValue());
    }

    @Test
    void shouldReturnOneForMissedNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0), LocalDateTime.of(2025, 10, 2, 7, 0), QualitySleep.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 23, 0), LocalDateTime.of(2025, 10, 4, 7, 0), QualitySleep.GOOD)
        );
        assertEquals(1L, function.apply(sessions).getValue());
    }

    @Test
    void shouldCountDaytimeSleepAsSleeplessNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0), LocalDateTime.of(2025, 10, 2, 7, 0), QualitySleep.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 14, 0), LocalDateTime.of(2025, 10, 2, 18, 0), QualitySleep.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 23, 0), LocalDateTime.of(2025, 10, 4, 7, 0), QualitySleep.GOOD)
        );
        assertEquals(1L, function.apply(sessions).getValue());
    }

    @Test
    void shouldCalculateCorrectlyForUnsortedSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 23, 0), LocalDateTime.of(2025, 10, 4, 7, 0), QualitySleep.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0), LocalDateTime.of(2025, 10, 2, 7, 0), QualitySleep.GOOD)
        );
        assertEquals(1L, function.apply(sessions).getValue());
    }

    @Test
    void shouldHandleFirstSessionAfterMidnight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 2, 0), LocalDateTime.of(2025, 10, 1, 7, 0), QualitySleep.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 0), LocalDateTime.of(2025, 10, 3, 7, 0), QualitySleep.GOOD)
        );
        assertEquals(1L, function.apply(sessions).getValue());
    }

    @Test
    void shouldHandleCrossMonthInterval() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 30, 23, 0), LocalDateTime.of(2025, 1, 31, 7, 0), QualitySleep.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 2, 1, 23, 0), LocalDateTime.of(2025, 2, 2, 7, 0), QualitySleep.GOOD)
        );
        assertEquals(1L, function.apply(sessions).getValue());
    }
}