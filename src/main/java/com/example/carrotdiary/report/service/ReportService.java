package com.example.carrotdiary.report.service;

import com.example.carrotdiary.report.dto.ReportRequestDto;
import com.example.carrotdiary.report.dto.ReportResponseDto;
import com.example.carrotdiary.report.entity.Report;
import com.example.carrotdiary.report.respository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public ReportResponseDto reportMember(ReportRequestDto reportRequestDto) {

        Report report = Report.addReport(reportRequestDto.getMemberId(), reportRequestDto.getReportType(), reportRequestDto.getContent());
        reportRepository.save(report);

        return new ReportResponseDto(report.getId());
    }

}
