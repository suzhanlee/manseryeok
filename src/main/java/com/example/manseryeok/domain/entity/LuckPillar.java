package com.example.manseryeok.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LuckPillar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;

    @Column(length = 2000)
    private String commonAdvice;

    @Column(length = 2000)
    private String manAdvice;

    @Column(length = 2000)
    private String girlAdvice;

    private String expression;
    private String celebrities;

    public static LuckPillar createLuckPillar(String date, String commonAdvice,
                                              String manAdvice, String girlAdvice,
                                              String expression, String celebrities) {
        LuckPillar luckPillar = new LuckPillar();

        luckPillar.date = date;
        luckPillar.commonAdvice = commonAdvice;
        luckPillar.manAdvice = manAdvice;
        luckPillar.girlAdvice = girlAdvice;
        luckPillar.expression = expression;
        luckPillar.celebrities = celebrities;

        return luckPillar;
    }
}
