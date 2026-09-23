package com.pragma.report.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CapacityReportResponseDto {
    private Long id;
    private String name;
    private List<TechnologyReportResponseDto> technologies;
}
