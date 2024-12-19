package com.example.manseryeok.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, List<String>> hourGanTable = new HashMap<>();

        hourGanTable.put("甲", List.of(
                "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙"
        ));

        hourGanTable.put("乙", List.of(
                "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁"
        ));

        hourGanTable.put("丙", List.of(
                "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己"
        ));

        hourGanTable.put("丁", List.of(
                "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛"
        ));

        hourGanTable.put("戊", List.of(
                "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"
        ));

        hourGanTable.put("己", List.of(
                "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙"
        ));

        hourGanTable.put("庚", List.of(
                "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁"
        ));

        hourGanTable.put("辛", List.of(
                "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己"
        ));

        hourGanTable.put("壬", List.of(
                "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛"

        ));

        hourGanTable.put("癸", List.of(
                "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"
        ));

        Gan gan = Arrays.stream(values())
                .filter(g -> g.character.equals(dayGan))
                .findFirst()
                .orElseThrow();

        int jiIndex = ji.ordinal();
        String findGan = hourGanTable.get(gan.character).get(jiIndex);
        return Arrays.stream(values())
                .filter(g -> g.character.equals(findGan))
                .findFirst()
                .orElseThrow();
    }
}
