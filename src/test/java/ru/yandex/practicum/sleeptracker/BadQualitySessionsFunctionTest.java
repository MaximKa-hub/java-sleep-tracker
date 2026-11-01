package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadQualitySessionsFunctionTest {
    private final BadQualitySessionsFunction function = new BadQualitySessionsFunction();
    private final LocalDateTime base = LocalDateTime.now();

    @Test
    void shouldReturnZeroForEmptyList() {
        assertEquals(0L, function.apply(Collections.emptyList()).getValue());
    }

    @Test
    void shouldReturnZeroWhenNoBadSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusHours(8), QualitySleep.GOOD),
                new SleepingSession(base, base.plusHours(8), QualitySleep.NORMAL)
        );
        assertEquals(0L, function.apply(sessions).getValue());
    }

    @Test
    void shouldCountSingleBadSession() {
        List<SleepingSession> sessions = List.of(new SleepingSession(base, base.plusHours(5), QualitySleep.BAD));
        assertEquals(1L, function.apply(sessions).getValue());
    }

    @Test
    void shouldCountMultipleBadSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusHours(2), QualitySleep.BAD),
                new SleepingSession(base.plusDays(1), base.plusDays(1).plusHours(3), QualitySleep.BAD)
        );
        assertEquals(2L, function.apply(sessions).getValue());
    }
}