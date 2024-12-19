package com.example.manseryeok.domain;

import com.github.usingsky.calendar.KoreanLunarCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public enum Term {
    SPRING_BEGINS(CalendarType.SOLAR, 2, 4, 5, 51),
    INSECTS_AWAKEN(CalendarType.SOLAR, 3, 6, 2, 43),
    SPRING_EQUINOX(CalendarType.SOLAR, 3, 21, 11, 33),
    PURE_BRIGHTNESS(CalendarType.SOLAR, 4, 5, 15, 13),
    GRAIN_RAIN(CalendarType.SOLAR, 4, 20, 22, 24),
    SUMMER_BEGINS(CalendarType.SOLAR, 5, 6, 3, 56),
    GRAIN_BUDS(CalendarType.SOLAR, 5, 21, 9, 48),
    GRAIN_IN_EAR(CalendarType.SOLAR, 6, 6, 17, 5),
    SUMMER_SOLSTICE(CalendarType.SOLAR, 6, 21, 23, 58),
    MINOR_HEAT(CalendarType.SOLAR, 7, 7, 8, 6),
    MAJOR_HEAT(CalendarType.SOLAR, 7, 23, 18, 14),
    AUTUMN_BEGINS(CalendarType.SOLAR, 8, 8, 4, 36),
    LIMIT_OF_HEAT(CalendarType.SOLAR, 8, 23, 16, 2),
    WHITE_DEW(CalendarType.SOLAR, 9, 8, 3, 32),
    AUTUMN_EQUINOX(CalendarType.SOLAR, 9, 23, 15, 4),
    COLD_DEW(CalendarType.SOLAR, 10, 8, 2, 22),
    FROST_DESCENT(CalendarType.SOLAR, 10, 23, 13, 27),
    WINTER_BEGINS(CalendarType.SOLAR, 11, 7, 23, 42),
    MINOR_SNOW(CalendarType.SOLAR, 11, 22, 9, 2),
    MAJOR_SNOW(CalendarType.SOLAR, 12, 7, 17, 46),
    WINTER_SOLSTICE(CalendarType.SOLAR, 12, 22, 1, 48),
    SPRING_BEGINS_LUNAR(CalendarType.LUNAR, 1, 1, 0, 0),
    INSECTS_AWAKEN_LUNAR(CalendarType.LUNAR, 2, 5, 0, 0),
    SPRING_EQUINOX_LUNAR(CalendarType.LUNAR, 3, 6, 0, 0);

    private final CalendarType calendarType;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    Term(CalendarType calendarType, int month, int day, int hour, int minute) {
        this.calendarType = calendarType;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public static Term findNearestSolarTerm(LocalDateTime birthDateTime, CalendarType calendarType) {
        long minMinutesDiff = Long.MAX_VALUE;
        Term nearestSolarTerm = null;

        for (Term term : values()) {
            if (term.calendarType == calendarType) {
                LocalDateTime termDateTime = getTermDateTime(birthDateTime, term, calendarType);
                long minutesDiff = Math.abs(ChronoUnit.MINUTES.between(birthDateTime, termDateTime));

                if (minutesDiff < minMinutesDiff) {
                    minMinutesDiff = minutesDiff;
                    nearestSolarTerm = term;
                }
            }
        }

        return nearestSolarTerm;
    }

    private static LocalDateTime getTermDateTime(LocalDateTime birthDateTime, Term term, CalendarType calendarType) {
        if (calendarType == CalendarType.SOLAR) {
            return LocalDateTime.of(birthDateTime.getYear(), term.month, term.day, term.hour, term.minute);
        } else {
            KoreanLunarCalendar lunarCalendar = KoreanLunarCalendar.getInstance();
            lunarCalendar.setLunarDate(birthDateTime.getYear(), term.month, term.day, false);
            LocalDate solarDate = LocalDate.parse(lunarCalendar.getSolarIsoFormat());
            return LocalDateTime.of(solarDate,
                    LocalDateTime.of(birthDateTime.getYear(), 1, 1, term.hour, term.minute).toLocalTime());
        }
    }

    public LocalDateTime createDateTime(int year) {
        return LocalDateTime.of(year, this.month, this.day, this.hour, this.minute);
    }
}
