package com.example.manseryeok.dto;

import com.example.manseryeok.domain.entity.LuckPillar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LuckPillarDto {

    private LuckPillar luckPillar;
    private boolean isComplete;
}
