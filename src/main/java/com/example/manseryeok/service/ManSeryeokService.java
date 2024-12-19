package com.example.manseryeok.service;

import com.example.manseryeok.domain.CalendarType;
import com.example.manseryeok.domain.CsvFileProcessor;
import com.example.manseryeok.domain.Gan;
import com.example.manseryeok.domain.GanJi;
import com.example.manseryeok.domain.Ji;
import com.example.manseryeok.domain.LuckPillarProcessor;
import com.example.manseryeok.domain.Term;
import com.example.manseryeok.domain.entity.LuckPillar;
import com.example.manseryeok.domain.entity.ManSeryeok;
import com.example.manseryeok.dto.LuckPillarDto;
import com.example.manseryeok.dto.rq.CreateDestinyRq;
import com.example.manseryeok.dto.rq.CreateLuckPillarsRq;
import com.example.manseryeok.dto.rs.CreateDestinyRs;
import com.example.manseryeok.dto.rs.CreateLuckPillarsRs;
import com.example.manseryeok.repository.LuckPillarRepository;
import com.example.manseryeok.repository.ManSeryeokRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ManSeryeokService {

    private final ManSeryeokRepository manSeryeokRepository;
    private final LuckPillarRepository luckPillarRepository;
    private final CsvFileProcessor csvFileProcessor;
    private final LuckPillarProcessor luckPillarProcessor;

    public void processManSeryeokCSVFile(MultipartFile file) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            br.readLine();
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                try {
                    ManSeryeok manSeryeok = csvFileProcessor.processLine(line, lineNumber);
                    manSeryeokRepository.save(manSeryeok);
                } catch (DateTimeException e) {
                    log.warn("{}번째 줄: 유효하지 않은 날짜 형식입니다 - {}", lineNumber, line);
                    continue;
                } catch (Exception e) {
                    log.error("{}번째 줄: 처리 중 오류가 발생했습니다 - {}", lineNumber, line, e);
                    continue;
                }
                lineNumber++;
            }
        }
    }

    public CreateDestinyRs calculateFourPillarsDestiny(CreateDestinyRq rq) {
        CalendarType calendarType = CalendarType.findByName(rq.getCalendarType());
        String threePillars = findThreePillarsByCalendarType(rq, calendarType);

        return createDestinyRs(rq, threePillars);
    }

    private String findThreePillarsByCalendarType(CreateDestinyRq rq, CalendarType calendarType) {
        if (calendarType == CalendarType.SOLAR) {
            return manSeryeokRepository.findSolarGanJiBySolarDate(rq.getBirthDate()).orElseThrow();
        } else {
            return manSeryeokRepository.findLunarGanJiByLunarDate(rq.getBirthDate()).orElseThrow();
        }
    }

    private CreateDestinyRs createDestinyRs(CreateDestinyRq rq, String threePillars) {
        if (rq.getBirthTime() != null) {
            String dayGan = extractDayGan(threePillars);
            GanJi ganji = GanJi.createHourPillar(rq.getBirthTime(), dayGan);
            return new CreateDestinyRs(threePillars, ganji.getGanJi());
        }

        return new CreateDestinyRs(threePillars);
    }

    private String extractDayGan(String ganji) {
        try {
            String[] parts = ganji.split("[年月日 ]");
            if (parts.length >= 3) {
                return parts[4].substring(0, 1);
            }
            throw new IllegalArgumentException("사주 형식이 올바르지 않습니다");
        } catch (Exception e) {
            throw new IllegalArgumentException("일간 추출에 실패했습니다: " + e.getMessage());
        }
    }

    public void processLuckPillarsCSVFile(MultipartFile file) throws IOException {
        try (CSVReader csvReader = new CSVReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String[] currentRow;

            while ((currentRow = csvReader.readNext()) != null) {
                LuckPillarDto luckPillarDto = luckPillarProcessor.process(currentRow);
                if (luckPillarDto.isComplete()) {
                    luckPillarRepository.save(luckPillarDto.getLuckPillar());
                }
            }

            saveLastLuckPillar();

        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveLastLuckPillar() {
        LuckPillar lastLuckPillar = luckPillarProcessor.createLuckPillar();
        luckPillarRepository.save(lastLuckPillar);
    }

    public CreateLuckPillarsRs calculateLuckPillars(CreateLuckPillarsRq rq) {
        LocalDateTime birthDateTime = rq.getBirthDateTime();
        CalendarType calendarType = CalendarType.findByName(rq.getCalendarType());
        Term nearestSolarTerm = Term.findNearestSolarTerm(birthDateTime, calendarType);

        LocalDateTime solarTermDateTime = nearestSolarTerm.createDateTime(birthDateTime.getYear());

        double daysDifference = ChronoUnit.MINUTES.between(birthDateTime, solarTermDateTime) / (24.0 * 60);

        int daeUnChangeCnt = (int) Math.round(daysDifference / 3);

        String gender = rq.getGender();
        String fourPillars = rq.getFourPillars();
        int direction = Gan.determineDirection(gender, String.valueOf(fourPillars.charAt(0)));

        String monthPillar = fourPillars.substring(4, 6);
        GanJi ganJi = new GanJi(
                Gan.findByName(monthPillar.substring(0, 1)), Ji.findByName(monthPillar.substring(1, 2)));

        String daeUn = calculateDaeUnByDirection(ganJi, daeUnChangeCnt, direction);
        LuckPillar luckPillar = luckPillarRepository.findByGanji(daeUn).orElseThrow();
        return createLuckPillarsRsByGender(gender, daeUn, luckPillar);
    }

    private String calculateDaeUnByDirection(GanJi ganJi, int daeUnChangeCnt, int direction) {
        if (direction == 1) {
            return ganJi.determineDaeUn(daeUnChangeCnt);
        }
        return ganJi.determineDaeUn(-daeUnChangeCnt);
    }

    private CreateLuckPillarsRs createLuckPillarsRsByGender(String gender, String daeUn, LuckPillar luckPillar) {
        if ("man".equals(gender)) {
            return new CreateLuckPillarsRs(daeUn, luckPillar.getCommonAdvice(), luckPillar.getManAdvice(),
                    luckPillar.getExpression(), luckPillar.getCelebrities());
        }
        return new CreateLuckPillarsRs(daeUn, luckPillar.getCommonAdvice(), luckPillar.getGirlAdvice(),
                luckPillar.getExpression(), luckPillar.getCelebrities());
    }
}
