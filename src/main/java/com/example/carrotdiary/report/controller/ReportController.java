package com.example.carrotdiary.report.controller;

import com.example.carrotdiary.report.dto.ReportRequestDto;
import com.example.carrotdiary.report.dto.ReportResponseDto;
import com.example.carrotdiary.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/report")
    public ResponseEntity<ReportResponseDto> reportDiary(@RequestBody ReportRequestDto reportRequestDto) {
        ReportResponseDto reportId = reportService.reportMember(reportRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(reportId);
    }
}
