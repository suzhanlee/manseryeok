package com.example.manseryeok.domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GanJi {

    private Gan gan;
    private Ji ji;

    public static GanJi createHourPillar(LocalTime time, String dayGan) {
        Ji ji = Ji.findByTime(time);
        Gan gan = Gan.findByJiAndDayGan(ji, dayGan);
        return new GanJi(gan, ji);
    }

    public String getGanJi() {
        return gan.getCharacter() + ji.getCharacter();
    }

    public String determineDaeUn(int daeUnChangeCnt) {
        List<String> sixtyGapJa = generateSixtyGapJa();

        int startIndex = sixtyGapJa.indexOf(this.gan.getCharacter() + this.ji.getCharacter());
        int daeUnIndex = (startIndex + daeUnChangeCnt + sixtyGapJa.size()) % sixtyGapJa.size();

        return sixtyGapJa.get(daeUnIndex);
    }

    private List<String> generateSixtyGapJa() {
        List<String> sixtyGapJa = new ArrayList<>();
        Gan[] gans = Gan.values();
        Ji[] jis = Ji.values();

        for (int i = 0; i < 60; i++) {
            Gan gan = gans[i % 10];
            Ji ji = jis[i % 12];
            sixtyGapJa.add(gan.getCharacter() + ji.getCharacter());
        }
        return sixtyGapJa;
    }
}
