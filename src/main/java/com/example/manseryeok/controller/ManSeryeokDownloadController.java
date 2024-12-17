package com.example.manseryeok.controller;

import com.example.manseryeok.service.ManSeryeokDownloadService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ManSeryeokDownloadController {

    private final ManSeryeokDownloadService manSeryeokDownloadService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSVData(@RequestParam("file") MultipartFile file) {
        try {
            manSeryeokDownloadService.processCSVFile(file);
            return ResponseEntity.ok("만세력 데이터 업로드 성공");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("만세력 데이터 업로드 실패: " + e.getMessage());
        }
    }
}
