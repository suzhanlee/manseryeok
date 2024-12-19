package com.example.manseryeok.domain;

import com.github.usingsky.calendar.KoreanLunarCalendar;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum Term {
    SPRING_BEGINS(CalendarType.SOLAR, 2, 4),
    INSECTS_AWAKEN(CalendarType.SOLAR, 3, 6),
    SPRING_EQUINOX(CalendarType.SOLAR, 3, 21),
    PURE_BRIGHTNESS(CalendarType.SOLAR, 4, 5),
    GRAIN_RAIN(CalendarType.SOLAR, 4, 20),
    SUMMER_BEGINS(CalendarType.SOLAR, 5, 6),
    GRAIN_BUDS(CalendarType.SOLAR, 5, 21),
    GRAIN_IN_EAR(CalendarType.SOLAR, 6, 6),
    SUMMER_SOLSTICE(CalendarType.SOLAR, 6, 21),
    MINOR_HEAT(CalendarType.SOLAR, 7, 7),
    MAJOR_HEAT(CalendarType.SOLAR, 7, 23),
    AUTUMN_BEGINS(CalendarType.SOLAR, 8, 8),
    LIMIT_OF_HEAT(CalendarType.SOLAR, 8, 23),
    WHITE_DEW(CalendarType.SOLAR, 9, 8),
    AUTUMN_EQUINOX(CalendarType.SOLAR, 9, 23),
    COLD_DEW(CalendarType.SOLAR, 10, 8),
    FROST_DESCENT(CalendarType.SOLAR, 10, 23),
    WINTER_BEGINS(CalendarType.SOLAR, 11, 7),
    MINOR_SNOW(CalendarType.SOLAR, 11, 22),
    MAJOR_SNOW(CalendarType.SOLAR, 12, 7),
    WINTER_SOLSTICE(CalendarType.SOLAR, 12, 22),
    SPRING_BEGINS_LUNAR(CalendarType.LUNAR, 1, 1),
    INSECTS_AWAKEN_LUNAR(CalendarType.LUNAR, 2, 5),
    SPRING_EQUINOX_LUNAR(CalendarType.LUNAR, 3, 6);

    private final CalendarType calendarType;
    private final int month;
    private final int day;

    Term(CalendarType calendarType, int month, int day) {
        this.calendarType = calendarType;
        this.month = month;
        this.day = day;
    }

    public static Term findNearestSolarTerm(LocalDate birthDate, CalendarType calendarType) {
        long minDaysDiff = Long.MAX_VALUE;
        Term nearestSolarTerm = null;

        for (Term term : values()) {
            if (term.calendarType == calendarType) {
                LocalDate termDate = getTermDate(birthDate, term, calendarType);
                long daysDiff = Math.abs(ChronoUnit.DAYS.between(birthDate, termDate));

                if (daysDiff < minDaysDiff) {
                    minDaysDiff = daysDiff;
                    nearestSolarTerm = term;
                }
            }
        }

        return nearestSolarTerm;
    }

    private static LocalDate getTermDate(LocalDate birthDate, Term term, CalendarType calendarType) {
        if (calendarType == CalendarType.SOLAR) {
            return LocalDate.of(birthDate.getYear(), term.month, term.day);
        } else {
            KoreanLunarCalendar lunarCalendar = KoreanLunarCalendar.getInstance();
            lunarCalendar.setLunarDate(birthDate.getYear(), term.month, term.day,
                    false);
            return LocalDate.parse(lunarCalendar.getSolarIsoFormat());
        }
    }

    public LocalDate createDate(int year) {
        return LocalDate.of(year, this.month, this.day);
    }
}
