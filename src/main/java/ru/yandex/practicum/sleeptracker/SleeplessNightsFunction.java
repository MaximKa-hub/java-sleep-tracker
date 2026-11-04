package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Количество бессоных ночей", 0L);
        }

        LocalDateTime firstStart = sessions.stream()
                .map(SleepingSession::getStart)
                .min(Comparator.naturalOrder())
                .orElseThrow();

        LocalDateTime lastEnd = sessions.stream()
                .map(SleepingSession::getEnd)
                .max(Comparator.naturalOrder())
                .orElseThrow();

        LocalDate startDate;
        if (firstStart.toLocalTime().isAfter(LocalTime.NOON)) {
            startDate = firstStart.toLocalDate();
        } else {
            startDate = firstStart.toLocalDate().minusDays(1);
        }

        LocalDate endDate = lastEnd.toLocalDate();

        long totalNights = ChronoUnit.DAYS.between(startDate, endDate);

        long sleptNights = sessions.stream().filter(this::isIntersectingNights).map(this::getMorningDate).distinct().count();

        return new SleepAnalysisResult<>("Количесто бессоных ночей", totalNights - sleptNights);
    }

    private boolean isIntersectingNights(SleepingSession session) {
        LocalDate morningDate = getMorningDate(session);
        LocalDateTime nightStart = morningDate.atStartOfDay();
        LocalDateTime nightEnd = morningDate.atTime(6, 0);

        return session.getStart().isBefore(nightEnd) && session.getEnd().isAfter(nightStart);
    }

    private LocalDate getMorningDate(SleepingSession session) {
        if (session.getStart().toLocalTime().isAfter(LocalTime.NOON)) {
            return session.getStart().toLocalDate().plusDays(1);
        } else {
            return session.getStart().toLocalDate();
        }
    }
}
