package com.pragma.report.application.mapper;

import com.pragma.report.application.dto.response.BootcampReportResponseDto;
import com.pragma.report.domain.model.BootcampReportModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBootcampReportResponseMapper {
    BootcampReportResponseDto toResponse(BootcampReportModel bootcampReportModel);
}
