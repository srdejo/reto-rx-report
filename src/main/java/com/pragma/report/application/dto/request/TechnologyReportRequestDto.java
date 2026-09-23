package com.pragma.report.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnologyReportRequestDto {
    @NotNull
    private Long id;

    private String name;
}
