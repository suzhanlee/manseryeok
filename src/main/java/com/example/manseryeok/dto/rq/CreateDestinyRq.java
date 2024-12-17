package com.example.manseryeok.dto.rq;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDestinyRq {

    private LocalDate birthDate;
    private String calendarType;
    private String sex;
    private String name;
    private LocalTime birthTime;
}
