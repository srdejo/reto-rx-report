package com.pragma.report.application.handler.impl;

import com.pragma.report.application.dto.request.ReportRequestDto;
import com.pragma.report.application.dto.response.ReportResponseDto;
import com.pragma.report.application.handler.IReportHandler;
import com.pragma.report.application.mapper.IReportRequestMapper;
import com.pragma.report.application.mapper.IReportResponseMapper;
import com.pragma.report.domain.api.IReportServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReportHandler implements IReportHandler {

    private final IReportServicePort reportServicePort;
    private final IReportRequestMapper reportRequestMapper;
    private final IReportResponseMapper reportResponseMapper;

    @Override
    public Mono<Void> saveReport(ReportRequestDto reportRequestDto) {
        return reportServicePort.saveReport(reportRequestMapper.toReport(reportRequestDto)).then();
    }

    @Override
    public Flux<ReportResponseDto> getAllReports() {
        return reportServicePort.getAllReports().map(reportResponseMapper::toResponse);
    }
}
