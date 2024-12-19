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

        Gan findDayGan = findByName(dayGan);

        int jiIndex = ji.ordinal();
        String findGan = hourGanTable.get(findDayGan.character).get(jiIndex);
        return findByName(findGan);
    }

    public static Gan findByName(String gan) {
        return Arrays.stream(values())
                .filter(g -> g.character.equals(gan))
                .findFirst()
                .orElseThrow();
    }

    public static int determineDirection(String gender, String yearGan) {
        Map<Gan, Integer> yearGanTable = new HashMap<>();

        yearGanTable.put(GAPS, 1);
        yearGanTable.put(EUL, -1);
        yearGanTable.put(BYUNG, 1);
        yearGanTable.put(JUNG, -1);
        yearGanTable.put(MU, 1);
        yearGanTable.put(GI, -1);
        yearGanTable.put(GYUNG, 1);
        yearGanTable.put(SHIN, -1);
        yearGanTable.put(IM, 1);
        yearGanTable.put(GYE, -1);

        Gan findYearGan = Arrays.stream(values())
                .filter(g -> g.character.equals(yearGan))
                .findFirst()
                .orElseThrow();

        if ("man".equals(gender)) {
            return yearGanTable.get(findYearGan);
        }
        return -yearGanTable.get(findYearGan);
    }
}
