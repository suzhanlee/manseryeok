package com.example.manseryeok.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDestinyRs {

    private String ganji;
    private String hourPillar;

    public CreateDestinyRs(String ganji) {
        this.ganji = ganji;
    }
}
