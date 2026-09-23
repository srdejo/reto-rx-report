package com.pragma.report.application.handler;

import com.pragma.report.application.dto.request.BootcampReportRequestDto;
import com.pragma.report.application.dto.response.BootcampReportResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcampReportHandler {

    Mono<Void> saveBootcampReport(BootcampReportRequestDto bootcampReportRequestDto);

    Flux<BootcampReportResponseDto> getAllBootcampReports();
}
