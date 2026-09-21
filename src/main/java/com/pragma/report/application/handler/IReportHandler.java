package com.pragma.report.application.handler;

import com.pragma.report.application.dto.request.ReportRequestDto;
import com.pragma.report.application.dto.response.ReportResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IReportHandler {

    Mono<Void> saveReport(ReportRequestDto reportRequestDto);

    Flux<ReportResponseDto> getAllReports();
}
