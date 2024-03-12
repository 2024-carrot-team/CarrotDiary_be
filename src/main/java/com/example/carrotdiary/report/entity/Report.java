package com.example.carrotdiary.report.entity;

import com.example.carrotdiary.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Report extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    private Long reportedId; // 신고된 ID (게시글의 memberId)

    @Enumerated(EnumType.STRING)
    private ReportType reportType;
    private String content;

    public static Report addReport(Long reportedId, ReportType reportType, String content) {
        Report report = new Report();
        report.reportedId = reportedId;
        report.reportType = reportType;
        report.content = content;

        return report;
    }
}
