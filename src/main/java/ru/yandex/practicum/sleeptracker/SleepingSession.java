package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    public static final LocalTime nightEnd = LocalTime.of(6, 0);

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final QualitySleep quality;

    SleepingSession(LocalDateTime start, LocalDateTime end, QualitySleep quality) {
        this.start = start;
        this.end = end;
        this.quality = quality;
    }

    public static SleepingSession parse(String line) {
        String[] parts = line.split(";");

        LocalDateTime start = LocalDateTime.parse(parts[0].trim(), FORMATTER);
        LocalDateTime end = LocalDateTime.parse(parts[1].trim(), FORMATTER);
        QualitySleep quality = QualitySleep.valueOf(parts[2].trim());

        return new SleepingSession(start, end, quality);
    }

    public boolean isNightSession() {
        if (!start.toLocalDate().equals(end.toLocalDate())) {
            return true;
        }
        return start.toLocalTime().isBefore(nightEnd);
    }

    public long getDurationInMinutes() {
        return Duration.between(start, end).toMinutes();
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public QualitySleep getQuality() {
        return quality;
    }
}
