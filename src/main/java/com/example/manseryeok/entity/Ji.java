package com.example.manseryeok.entity;

import java.time.LocalTime;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Ji {
    JA("子", LocalTime.of(23, 0), LocalTime.of(1, 0)),
    CHUK("丑", LocalTime.of(1, 0), LocalTime.of(3, 0)),
    IN("寅", LocalTime.of(3, 0), LocalTime.of(5, 0)),
    MYO("卯", LocalTime.of(5, 0), LocalTime.of(7, 0)),
    JIN("辰", LocalTime.of(7, 0), LocalTime.of(9, 0)),
    SA("巳", LocalTime.of(9, 0), LocalTime.of(11, 0)),
    O("午", LocalTime.of(11, 0), LocalTime.of(13, 0)),
    MI("未", LocalTime.of(13, 0), LocalTime.of(15, 0)),
    SIN("申", LocalTime.of(15, 0), LocalTime.of(17, 0)),
    YU("酉", LocalTime.of(17, 0), LocalTime.of(19, 0)),
    SUL("戌", LocalTime.of(19, 0), LocalTime.of(21, 0)),
    HAE("亥", LocalTime.of(21, 0), LocalTime.of(23, 0));

    private final String character;
    private final LocalTime startTime;
    private final LocalTime endTime;

    Ji(String character, LocalTime startTime, LocalTime endTime) {
        this.character = character;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Ji findByTime(LocalTime time) {
        return Arrays.stream(values())
                .filter(ji -> isTimeInRange(time, ji.startTime, ji.endTime))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다 : " + time));
    }

    private static boolean isTimeInRange(LocalTime time, LocalTime start, LocalTime end) {
        if (start.equals(LocalTime.of(23, 0))) {
            if (!time.isBefore(LocalTime.of(23, 0))) {
                return true;
            }
            return time.isBefore(LocalTime.of(1, 0));
        }
        return !time.isBefore(start) && time.isBefore(end);
    }

    public static Ji findByName(String name) {
        return Arrays.stream(values())
                .filter(j -> j.character.equals(name))
                .findFirst()
                .orElseThrow();
    }

    public static Ji changeJi(Ji startJi, int count, CalendarType calendarType, String gender) {
        if (count == 0) {
            return startJi;
        }
        boolean isForward = (CalendarType.SOLAR.equals(calendarType) && "man".equals(gender))
                || (CalendarType.LUNAR.equals(calendarType) && "woman".equals(gender));
        return startJi.getNextJi(count, isForward);
    }

    private Ji getNextJi(int count, boolean isForward) {
        Ji[] values = Ji.values();
        int currentIndex = this.ordinal();

        if (isForward) {
            return values[(currentIndex + count) % values.length];
        } else {
            int newIndex = (currentIndex - count) % values.length;
            if (newIndex < 0) {
                newIndex += values.length;
            }
            return values[newIndex];
        }
    }
}
