package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу в качестве аргумента командной строки");
            return;
        }

        String filePath = args[0];
        try {
            List<SleepingSession> sessions = loadSession(filePath);
            FUNCTION_LIST.forEach(function ->
                    System.out.println(function.apply(sessions)));
        } catch (IOException e) {
            System.out.println("Ошибка при чтение файла " + e.getMessage());
        }

    }

    public static List<SleepingSession> loadSession(String file) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(file), StandardCharsets.UTF_8)) {
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