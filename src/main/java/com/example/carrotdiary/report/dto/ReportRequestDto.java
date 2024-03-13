package com.example.carrotdiary.report.dto;

import com.example.carrotdiary.report.entity.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportRequestDto {

    private Long memberId;
    private ReportType reportType;
    private String content;
}
