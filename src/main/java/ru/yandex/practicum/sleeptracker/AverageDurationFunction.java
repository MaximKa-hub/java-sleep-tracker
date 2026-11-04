package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

//Функция для расчета средней продолжительности сессии

public class AverageDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Double>> {

    @Override
    public SleepAnalysisResult<Double> apply(List<SleepingSession> sessions) {
        double averageDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .average()
                .orElse(0.0);
        return new SleepAnalysisResult<>("средняя продолжительность сессии", averageDuration);
    }
}