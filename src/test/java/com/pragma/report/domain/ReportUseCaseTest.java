package com.pragma.report.domain;

import com.pragma.report.domain.model.ReportModel;
import com.pragma.report.domain.spi.IReportPersistencePort;
import com.pragma.report.domain.usecase.ReportUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ReportUseCaseTest {

    private final IReportPersistencePort port = Mockito.mock(IReportPersistencePort.class);
    private final ReportUseCase useCase = new ReportUseCase(port);

    @Test
    void saveReportDelegatesToPort() {
        ReportModel model = new ReportModel(null, "test");
        Mockito.when(port.saveReport(model)).thenReturn(Mono.just(model));

        StepVerifier.create(useCase.saveReport(model)).expectNext(model).verifyComplete();
    }

    @Test
    void getAllReportsReturnsFlux() {
        ReportModel model = new ReportModel(null, "test");
        Mockito.when(port.getAllReports()).thenReturn(Flux.just(model));

        StepVerifier.create(useCase.getAllReports()).expectNext(model).verifyComplete();
    }
}
