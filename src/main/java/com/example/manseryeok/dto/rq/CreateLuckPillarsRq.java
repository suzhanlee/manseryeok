package com.example.manseryeok.dto.rq;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLuckPillarsRq {

    private String fourPillars;
    private String gender;
    private LocalDateTime birthDateTime;
    private String calendarType;
}
