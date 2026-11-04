package ru.yandex.practicum.sleeptracker;

public enum ChronoType {
    OWL("Сова"),
    LARK("Жаворонок"),
    PIGEON("Голубь");

    private final String title;

    ChronoType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
