package com.pragma.report.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CapacityReportRequestDto {
    @NotNull
    private Long id;

    private String name;

    @Valid
    private List<TechnologyReportRequestDto> technologies;
}
