package com.pragma.report.domain.spi;

import com.pragma.report.domain.model.ReportModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IReportPersistencePort {
    Mono<ReportModel> saveReport(ReportModel reportModel);

    Flux<ReportModel> getAllReports();
}
