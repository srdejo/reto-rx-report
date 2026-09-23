package com.pragma.report.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BootcampReportModel {
    private Long bootcampId;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer durationDays;
    private List<CapacityReportModel> capacities;
    private Integer capacityCount;
    private Integer technologyCount;
    private Long enrolledCount;
    private LocalDateTime updatedAt;
}
