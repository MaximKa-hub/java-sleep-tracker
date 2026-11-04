package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

//Функция для расчета минимальной продолжительности сессии

public class MinDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long minDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .min()
                .orElse(0L);
        return new SleepAnalysisResult<>("Минимальная продолжительность сессии", minDuration);
    }
}
