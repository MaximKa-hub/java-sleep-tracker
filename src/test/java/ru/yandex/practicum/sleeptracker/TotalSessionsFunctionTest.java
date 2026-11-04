package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalSessionsFunctionTest {
    private final TotalSessionsFunction function = new TotalSessionsFunction();

    @Test
    void shouldReturnZeroForEmptyList() {
        assertEquals(0L, function.apply(Collections.emptyList()).getValue());
    }

    @Test
    void shouldReturnOneForSingleSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(8), QualitySleep.GOOD)
        );
        assertEquals(1L, function.apply(sessions).getValue());
    }

    @Test
    void shouldReturnCountForMultipleSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusHours(8), QualitySleep.GOOD),
                new SleepingSession(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(7), QualitySleep.NORMAL)
        );
        assertEquals(2L, function.apply(sessions).getValue());
    }
}