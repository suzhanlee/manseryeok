package com.example.manseryeok.domain;

import com.example.manseryeok.domain.entity.ManSeryeok;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class CsvFileProcessor {

    public ManSeryeok processLine(String line, int lineNumber) {
        String[] data = line.split(",");
        try {
            return ManSeryeok.createCalendar(
                    data[0],
                    LocalDate.parse(data[1]),
                    data[2],
                    data[3],
                    LocalDate.parse(data[4]),
                    data[5]
            );
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(lineNumber + "번째 줄: 데이터 형식이 올바르지 않습니다");
        }
    }

}
