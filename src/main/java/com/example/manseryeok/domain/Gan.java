package com.example.manseryeok.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Gan {
    GAPS("甲"),
    EUL("乙"),
    BYUNG("丙"),
    JUNG("丁"),
    MU("戊"),
    GI("己"),
    GYUNG("庚"),
    SHIN("辛"),
    IM("壬"),
    GYE("癸");

    private final String character;

    Gan(String character) {
        this.character = character;
    }

    public static Gan findByJiAndDayGan(Ji ji, String dayGan) {
        int jiIndex = ji.ordinal();
        int dayGanIndex = Arrays.stream(values())
                .filter(gan -> gan.character.equals(dayGan))
                .findFirst()
                .orElseThrow()
                .ordinal();

//        int baseGan = (dayGanIndex / 2) * 2;
//        int hourGanIndex = (baseGan + jiIndex / 2) % 10;
        int hourGanIndex = (dayGanIndex + (jiIndex + 1) / 2) % 10;
        return values()[hourGanIndex];
    }
}
