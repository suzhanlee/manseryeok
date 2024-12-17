package com.example.manseryeok.controller;

import com.example.manseryeok.dto.rq.CreateDestinyRq;
import com.example.manseryeok.dto.rq.CreateLuckPillarsRq;
import com.example.manseryeok.dto.rs.CreateDestinyRs;
import com.example.manseryeok.dto.rs.CreateLuckPillarsRs;
import com.example.manseryeok.service.ManSeryeokService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ManSeryeokController {

    private final ManSeryeokService manSeryeokService;

    @PostMapping("/manSeryeok/upload")
    public ResponseEntity<String> uploadManSeryeokData(@RequestParam("file") MultipartFile file) {
        try {
            manSeryeokService.processManSeryeokCSVFile(file);
            return ResponseEntity.ok("만세력 데이터 업로드 성공");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("만세력 데이터 업로드 실패: " + e.getMessage());
        }
    }

    @PostMapping("/manSeryeok")
    public ResponseEntity<CreateDestinyRs> createDestiny(@RequestBody CreateDestinyRq rq) {
        CreateDestinyRs rs = manSeryeokService.calculateFourPillarsDestiny(rq);
        return ResponseEntity.ok(rs);
    }

    @PostMapping("/luckPillars/upload")
    public ResponseEntity<String> uploadLuckPillarsData(@RequestParam("file") MultipartFile file) {
        try {
            manSeryeokService.processLuckPillarsCSVFile(file);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("대운 데이터 업로드 실패: " + e.getMessage());
        }
        return ResponseEntity.ok("대운 데이터 업로드 성공");
    }

    @PostMapping("/luckPillars")
    public ResponseEntity<CreateLuckPillarsRs> createLuckPillars(@RequestBody CreateLuckPillarsRq rq) {
        CreateLuckPillarsRs rs = manSeryeokService.calculateLuckPillars(rq);
        return ResponseEntity.ok(rs);
    }
}
