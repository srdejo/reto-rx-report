package com.pragma.report.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BootcampReportResponseDto {
    private Long bootcampId;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer durationDays;
    private List<CapacityReportResponseDto> capacities;
    private Integer capacityCount;
    private Integer technologyCount;
    private Long enrolledCount;
    private List<EnrolledPersonReportResponseDto> enrolledPersons;
    private LocalDateTime updatedAt;
}
