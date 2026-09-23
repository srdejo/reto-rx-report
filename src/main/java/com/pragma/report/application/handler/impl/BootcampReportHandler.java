package com.pragma.report.application.handler.impl;

import com.pragma.report.application.dto.request.BootcampReportRequestDto;
import com.pragma.report.application.dto.response.BootcampReportResponseDto;
import com.pragma.report.application.handler.IBootcampReportHandler;
import com.pragma.report.application.mapper.IBootcampReportRequestMapper;
import com.pragma.report.application.mapper.IBootcampReportResponseMapper;
import com.pragma.report.domain.api.IBootcampReportServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BootcampReportHandler implements IBootcampReportHandler {

    private final IBootcampReportServicePort bootcampReportServicePort;
    private final IBootcampReportRequestMapper bootcampReportRequestMapper;
    private final IBootcampReportResponseMapper bootcampReportResponseMapper;

    @Override
    public Mono<Void> saveBootcampReport(BootcampReportRequestDto bootcampReportRequestDto) {
        return bootcampReportServicePort
                .saveBootcampReport(bootcampReportRequestMapper.toModel(bootcampReportRequestDto))
                .then();
    }

    @Override
    public Flux<BootcampReportResponseDto> getAllBootcampReports() {
        return bootcampReportServicePort.getAllBootcampReports().map(bootcampReportResponseMapper::toResponse);
    }
}
