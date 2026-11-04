package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

//Функция для поиска сумарного количество сессий

public class TotalSessionsFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        long count = sleepingSessions.size();
        return new SleepAnalysisResult<>("Общее количество сессий сна", count);
    }
}
