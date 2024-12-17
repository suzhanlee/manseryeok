package com.example.manseryeok.domain;

import java.time.LocalTime;

public class HourPillar {

    private final Gan gan;
    private final Ji ji;

    private HourPillar(Gan gan, Ji ji) {
        this.gan = gan;
        this.ji = ji;
    }

    public static HourPillar createHourPillar(LocalTime time, String dayGan) {
        Ji ji = Ji.findByTime(time);
        Gan gan = Gan.findByJiAndDayGan(ji, dayGan);
        return new HourPillar(gan, ji);
    }

    public String getGanJi() {
        return gan.getCharacter() + ji.getCharacter();
    }
}
