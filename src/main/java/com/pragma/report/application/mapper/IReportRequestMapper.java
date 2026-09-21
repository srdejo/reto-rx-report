package com.pragma.report.application.mapper;

import com.pragma.report.application.dto.request.ReportRequestDto;
import com.pragma.report.domain.model.ReportModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IReportRequestMapper {
    ReportModel toReport(ReportRequestDto reportRequestDto);
}
