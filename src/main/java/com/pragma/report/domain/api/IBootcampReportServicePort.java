package com.pragma.report.domain.api;

import com.pragma.report.domain.model.BootcampReportModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcampReportServicePort {

    Mono<BootcampReportModel> saveBootcampReport(BootcampReportModel bootcampReportModel);

    Flux<BootcampReportModel> getAllBootcampReports();
}
