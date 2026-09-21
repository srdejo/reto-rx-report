package com.pragma.report.domain.api;

import com.pragma.report.domain.model.ReportModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IReportServicePort {

    Mono<ReportModel> saveReport(ReportModel reportModel);

    Flux<ReportModel> getAllReports();
}
