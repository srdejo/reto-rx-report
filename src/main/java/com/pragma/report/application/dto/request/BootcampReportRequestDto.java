package com.pragma.report.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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

    @NotNull
    @PositiveOrZero
    private Long enrolledCount;
}
