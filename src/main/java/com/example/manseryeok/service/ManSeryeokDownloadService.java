package com.example.manseryeok.service;

import com.example.manseryeok.entity.ManSeryeok;
import com.example.manseryeok.repository.ManSeryeokRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class ManSeryeokDownloadService {

    private final ManSeryeokRepository manSeryeokRepository;

    @Transactional
    public void processCSVFile(MultipartFile file) throws IOException {
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
}
