package com.example.manseryeok.dto.rq;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLuckPillarsRq {

    private String fourPillars;
    private String gender;
    private LocalDate birthDate;
    private String calendarType;
    private LocalTime birthTime;
}
