package com.example.carrotdiary.report.respository;

import com.example.carrotdiary.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
