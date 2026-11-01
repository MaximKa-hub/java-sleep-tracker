package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    public static void main(String[] args) {
        String timeFile = "sleep_log.txt";

        try {
            List<SleepingSession> sessions = loadSession(timeFile);

            if (sessions.isEmpty()) {
                System.out.println("Файл пуст или содежрит неверные данные");
                return;
            }
            FUNCTION_LIST.forEach(function ->
                    System.out.println(function.apply(sessions)));
        } catch (IOException e) {
            System.out.println("Ошибка при чтение файла " + e.getMessage());
        }

    }

    public static List<SleepingSession> loadSession(String file) throws IOException {
        InputStream from = SleepTrackerApp.class.getClassLoader().getResourceAsStream(file);
        if (from == null) {
            throw new IOException("Файл не найден в папке resources" + file);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(from, StandardCharsets.UTF_8))) {
            return reader.lines()
                    .filter(line -> !line.isBlank())
                    .map(SleepingSession::parse)
                    .collect(Collectors.toList());
        }
    }

    private static final List<Function<List<SleepingSession>, ? extends SleepAnalysisResult<?>>> FUNCTION_LIST = List.of(
            new AverageDurationFunction(),
            new BadQualitySessionsFunction(),
            new MaxDurationFunction(),
            new MinDurationFunction(),
            new TotalSessionsFunction(),
            new ChronoTypeFunction(),
            new SleeplessNightsFunction()
    );
}