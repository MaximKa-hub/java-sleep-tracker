package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronoTypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult<ChronoType>> {

    @Override
    public SleepAnalysisResult<ChronoType> apply(List<SleepingSession> sessions) {
        Map<ChronoType, Long> counts = sessions.stream()
                .filter(this::isValidNightSession)
                .map(this::determineType)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        ChronoType resultType = counts.entrySet().stream()
                .reduce((e1, e2) -> {
                    if (e1.getValue() > e2.getValue()) return e1;
                    if (e2.getValue() > e1.getValue()) return e2;
                    return Map.entry(ChronoType.PIGEON, e1.getValue());
                })
                .map(Map.Entry::getKey)
                .orElse(ChronoType.PIGEON);
        return new SleepAnalysisResult<>("Хронотип пользователя", resultType);
    }

    private boolean isValidNightSession(SleepingSession session) {

        LocalDate dayDate;

        if (session.getStart().toLocalTime().isAfter(LocalTime.NOON)) {
            dayDate = session.getStart().toLocalDate().plusDays(1);
        } else {
            dayDate = session.getStart().toLocalDate();
        }

        LocalDateTime nightStart = dayDate.atStartOfDay();
        LocalDateTime nightEnd = dayDate.atTime(6, 0);
        return session.getStart().isBefore(nightEnd) && session.getEnd().isAfter(nightStart);
    }

    private ChronoType determineType(SleepingSession session) {
        LocalTime start = session.getStart().toLocalTime();
        LocalTime end = session.getEnd().toLocalTime();

        boolean isOwlStart = start.isAfter(LocalTime.of(23, 0)) ||
                start.isBefore(LocalTime.of(6, 0));
        boolean isOwlEnd = end.isAfter(LocalTime.of(9, 0));

        boolean isLarkStart = start.isAfter(LocalTime.NOON) &&
                start.isBefore(LocalTime.of(22, 0));
        boolean isLarkEnd = end.isBefore(LocalTime.of(7, 0));

        if (isOwlStart && isOwlEnd) {
            return ChronoType.OWL;
        }

        if (isLarkStart && isLarkEnd) {
            return ChronoType.LARK;
        }
        return ChronoType.PIGEON;
    }
}
