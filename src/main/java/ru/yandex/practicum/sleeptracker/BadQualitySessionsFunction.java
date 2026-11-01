package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

//Функция для подсчета плохих сессий

public class BadQualitySessionsFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long badSessionsCount = sessions.stream()
                .filter(session -> session.getQuality() == QualitySleep.BAD)
                .count();
        return new SleepAnalysisResult<>("Количество плохих сессий", badSessionsCount);
    }
}
