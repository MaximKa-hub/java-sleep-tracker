package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

//Функция для расчета максимальной продолжительности сессии

public class MaxDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long maxDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .max()
                .orElse(0L);
        return new SleepAnalysisResult<>("Максимальная продолжительность сессии", maxDuration);
    }
}
