package com.example.manseryeok.entity;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Luna {

    private LocalDate lunarDate;
    private String lunarGanJi;

    public Luna(LocalDate lunarDate, String lunarGanJi) {
        this.lunarDate = lunarDate;
        this.lunarGanJi = lunarGanJi;
    }
}
