package com.example.manseryeok.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManSeryeok {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayOfWeek;
    private LocalDate solarDate;
    private String solarGanJi;
    private String jeolgi;

    @Embedded
    private Luna luna;

    public static ManSeryeok createCalender(String dayOfWeek, LocalDate solarDate,
                                            String solarGanJi, String jeolgi,
                                            LocalDate lunarDate, String lunarGanJi) {
        ManSeryeok manSeryeok = new ManSeryeok();

        manSeryeok.dayOfWeek = dayOfWeek;
        manSeryeok.solarDate = solarDate;
        manSeryeok.solarGanJi = solarGanJi;
        manSeryeok.jeolgi = jeolgi;
        manSeryeok.luna = new Luna(lunarDate, lunarGanJi);

        return manSeryeok;
    }
}
