package com.pragma.report.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BootcampReportRequestDto {
    @NotNull
    private Long bootcampId;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @Positive
    private Integer durationDays;

    @Valid
    private List<CapacityReportRequestDto> capacities;

    @Valid
    private List<EnrolledPersonReportRequestDto> enrolledPersons;

    @NotNull
    private LocalDateTime snapshotAt;
}
