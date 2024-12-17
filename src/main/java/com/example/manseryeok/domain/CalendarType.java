package com.example.manseryeok.domain;

import java.util.Arrays;

public enum CalendarType {

    SOLAR("양력"), LUNAR("음력");

    private final String name;

    CalendarType(String name) {
        this.name = name;
    }

    public static CalendarType findByName(String name) {
        return Arrays.stream(values())
                .filter(type -> type.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("타입을 찾을 수 없습니다 : " + name));
    }
}
