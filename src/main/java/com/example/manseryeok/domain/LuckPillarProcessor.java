package com.example.manseryeok.domain;

import com.example.manseryeok.domain.entity.LuckPillar;
import com.example.manseryeok.dto.LuckPillarDto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class LuckPillarProcessor {

    private String currentFirstField = null;
    private StringBuilder secondFieldBuilder = new StringBuilder();
    private StringBuilder thirdFieldBuilder = new StringBuilder();
    private StringBuilder fourthFieldBuilder = new StringBuilder();
    private String fifthField = null;
    private String sixthField = null;

    public LuckPillarDto process(String[] currentRow) {
        if (currentRow[0] != null && !currentRow[0].trim().isEmpty()) {
            if (currentFirstField != null && !currentFirstField.isEmpty()) {
                LuckPillar luckPillar = createLuckPillar();
                clearField(secondFieldBuilder, thirdFieldBuilder, fourthFieldBuilder);
                initNextLuckPillar(currentRow);
                return new LuckPillarDto(luckPillar, true);
            }

            initNextLuckPillar(currentRow);

        } else {
            if (currentRow.length > 1) {
                if (currentRow[1].trim().contains("남자특징")) {
                    if (currentRow.length > 2) {
                        thirdFieldBuilder.append(currentRow[2].trim());
                    }
                } else if (currentRow[1].trim().contains("여자특징")) {
                    if (currentRow.length > 2) {
                        fourthFieldBuilder.append(currentRow[2].trim());
                    }
                } else if (currentRow[1].trim().contains("표현")) {
                    if (currentRow.length > 2) {
                        fifthField = currentRow[2].trim();
                    }
                } else if (currentRow[1].trim().contains("유명인")) {
                    if (currentRow.length > 2) {
                        sixthField = currentRow[2].trim();
                    }
                }
            }
        }
        return new LuckPillarDto(createLuckPillar(), false);
    }

    private void initNextLuckPillar(String[] currentRow) {
        if (currentRow.length > 1 && currentRow[1].trim().contains("남녀공통특징")) {
            if (currentRow.length > 2) {
                secondFieldBuilder.append(currentRow[2].trim());
            }
        }

        currentFirstField = currentRow[0].trim();
    }

    public LuckPillar createLuckPillar() {
        return LuckPillar.createLuckPillar(
                currentFirstField,
                secondFieldBuilder.toString().trim(),
                thirdFieldBuilder.toString().trim(),
                fourthFieldBuilder.toString().trim(),
                fifthField,
                sixthField);
    }

    private void clearField(StringBuilder secondFieldBuilder, StringBuilder thirdFieldBuilder,
                            StringBuilder fourthFieldBuilder) {
        secondFieldBuilder.setLength(0);
        thirdFieldBuilder.setLength(0);
        fourthFieldBuilder.setLength(0);
        currentFirstField = null;
        fifthField = null;
        sixthField = null;
    }
}
