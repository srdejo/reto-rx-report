package com.pragma.report.domain.usecase;

import com.pragma.report.domain.api.IReportServicePort;
import com.pragma.report.domain.model.ReportModel;
import com.pragma.report.domain.spi.IReportPersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReportUseCase implements IReportServicePort {

    private final IReportPersistencePort reportPersistencePort;

    public ReportUseCase(IReportPersistencePort reportPersistencePort) {
        this.reportPersistencePort = reportPersistencePort;
    }

    @Override
    public Mono<ReportModel> saveReport(ReportModel reportModel) {
        return reportPersistencePort.saveReport(reportModel);
    }

    @Override
    public Flux<ReportModel> getAllReports() {
        return reportPersistencePort.getAllReports();
    }
}
