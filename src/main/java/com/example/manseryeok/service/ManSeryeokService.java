package com.example.manseryeok.service;

import com.example.manseryeok.dto.rq.CreateDestinyRq;
import com.example.manseryeok.dto.rs.CreateDestinyRs;
import com.example.manseryeok.entity.CalendarType;
import com.example.manseryeok.entity.HourPillar;
import com.example.manseryeok.entity.LuckPillar;
import com.example.manseryeok.entity.ManSeryeok;
import com.example.manseryeok.repository.LuckPillarRepository;
import com.example.manseryeok.repository.ManSeryeokRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDate;
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

    public void processManSeryeokCSVFile(MultipartFile file) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            br.readLine();
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                try {
                    processLine(line, lineNumber);
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

    private void processLine(String line, int lineNumber) {
        String[] data = line.split(",");
        try {
            ManSeryeok manSeryeok = ManSeryeok.createCalender(
                    data[0],
                    LocalDate.parse(data[1]),
                    data[2],
                    data[3],
                    LocalDate.parse(data[4]),
                    data[5]
            );
            manSeryeokRepository.save(manSeryeok);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(lineNumber + "번째 줄: 데이터 형식이 올바르지 않습니다");
        }
    }

    public CreateDestinyRs calculateFourPillarsDestiny(CreateDestinyRq rq) {
        CalendarType calendarType = CalendarType.findByName(rq.getCalendarType());
        String threePillars = findThreePillarsByCalenderType(rq, calendarType);

        return createDestinyRs(rq, threePillars);
    }

    private String findThreePillarsByCalenderType(CreateDestinyRq rq, CalendarType calendarType) {
        if (calendarType == CalendarType.SOLAR) {
            return manSeryeokRepository.findSolarGanJiBySolarDate(rq.getBirthDate()).orElseThrow();
        } else {
            return manSeryeokRepository.findLunarGanJiByLunarDate(rq.getBirthDate()).orElseThrow();
        }
    }

    private CreateDestinyRs createDestinyRs(CreateDestinyRq rq, String threePillars) {
        if (rq.getBirthTime() != null) {
            String dayGan = extractDayGan(threePillars);
            HourPillar hourPillar = HourPillar.createHourPillar(rq.getBirthTime(), dayGan);
            return new CreateDestinyRs(threePillars, hourPillar.getGanJi());
        }

        return new CreateDestinyRs(threePillars);
    }

    private static String extractDayGan(String ganji) {
        try {
            String[] parts = ganji.split("[年月日 ]");
            if (parts.length >= 3) {
                return parts[2].substring(0, 1);
            }
            throw new IllegalArgumentException("사주 형식이 올바르지 않습니다");
        } catch (Exception e) {
            throw new IllegalArgumentException("일간 추출에 실패했습니다: " + e.getMessage());
        }
    }
}