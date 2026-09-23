package com.pragma.report.domain.spi;

import com.pragma.report.domain.model.BootcampReportModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcampReportPersistencePort {

    Mono<BootcampReportModel> saveBootcampReport(BootcampReportModel bootcampReportModel);

    Flux<BootcampReportModel> getAllBootcampReports();
}
