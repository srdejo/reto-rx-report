package com.pragma.report.domain;

import com.pragma.report.domain.model.BootcampReportModel;
import com.pragma.report.domain.model.CapacityReportModel;
import com.pragma.report.domain.model.EnrolledPersonReportModel;
import com.pragma.report.domain.model.TechnologyReportModel;
import com.pragma.report.domain.spi.IBootcampReportPersistencePort;
import com.pragma.report.domain.usecase.BootcampReportUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BootcampReportUseCaseTest {

    private final IBootcampReportPersistencePort persistencePort = mock(IBootcampReportPersistencePort.class);
    private final BootcampReportUseCase useCase = new BootcampReportUseCase(persistencePort);

    @Test
    void saveBootcampReportCalculatesCountsAndPersists() {
        TechnologyReportModel java = new TechnologyReportModel(1L, "Java");
        TechnologyReportModel spring = new TechnologyReportModel(2L, "Spring");
        TechnologyReportModel sql = new TechnologyReportModel(3L, "SQL");
        List<CapacityReportModel> capacities = List.of(
                new CapacityReportModel(1L, "Backend", List.of(java, spring)),
                new CapacityReportModel(2L, "Data", List.of(java, sql)));
        BootcampReportModel report = report(capacities, List.of(
                person(1L, "Ana", "ana@mail.com"), person(2L, "Luis", "luis@mail.com"),
                person(3L, "Eva", "eva@mail.com"), person(4L, "Juan", "juan@mail.com")));
        givenPersistenceEchoesInput();

        StepVerifier.create(useCase.saveBootcampReport(report))
                .assertNext(saved -> {
                    assertThat(saved.getBootcampId()).isEqualTo(10L);
                    assertThat(saved.getCapacityCount()).isEqualTo(2);
                    assertThat(saved.getTechnologyCount()).isEqualTo(3);
                    assertThat(saved.getEnrolledCount()).isEqualTo(4L);
                    assertThat(saved.getEnrolledPersons()).extracting(EnrolledPersonReportModel::getEmail)
                            .contains("ana@mail.com", "juan@mail.com");
                    assertThat(saved.getUpdatedAt()).isNotNull();
                })
                .verifyComplete();

        ArgumentCaptor<BootcampReportModel> captor = ArgumentCaptor.forClass(BootcampReportModel.class);
        verify(persistencePort).saveBootcampReport(captor.capture());
        assertThat(captor.getValue().getTechnologyCount()).isEqualTo(3);
    }

    @Test
    void saveBootcampReportWithoutCapacitiesNorPersonsHasZeroCounts() {
        BootcampReportModel report = report(null, null);
        givenPersistenceEchoesInput();

        StepVerifier.create(useCase.saveBootcampReport(report))
                .assertNext(saved -> {
                    assertThat(saved.getCapacities()).isEmpty();
                    assertThat(saved.getCapacityCount()).isZero();
                    assertThat(saved.getTechnologyCount()).isZero();
                    assertThat(saved.getEnrolledPersons()).isEmpty();
                    assertThat(saved.getEnrolledCount()).isZero();
                })
                .verifyComplete();
    }

    @Test
    void saveBootcampReportIgnoresCapacitiesWithoutTechnologies() {
        List<CapacityReportModel> capacities = List.of(
                new CapacityReportModel(1L, "Backend", null),
                new CapacityReportModel(2L, "Data", List.of(new TechnologyReportModel(3L, "SQL"))));
        BootcampReportModel report = report(capacities, List.of());
        givenPersistenceEchoesInput();

        StepVerifier.create(useCase.saveBootcampReport(report))
                .assertNext(saved -> {
                    assertThat(saved.getCapacityCount()).isEqualTo(2);
                    assertThat(saved.getTechnologyCount()).isEqualTo(1);
                })
                .verifyComplete();
    }

    @Test
    void getAllBootcampReportsDelegatesToPort() {
        BootcampReportModel report = report(List.of(), List.of());
        when(persistencePort.getAllBootcampReports()).thenReturn(Flux.just(report));

        StepVerifier.create(useCase.getAllBootcampReports())
                .expectNext(report)
                .verifyComplete();
    }

    private void givenPersistenceEchoesInput() {
        when(persistencePort.saveBootcampReport(any()))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
    }

    private BootcampReportModel report(List<CapacityReportModel> capacities,
                                       List<EnrolledPersonReportModel> enrolledPersons) {
        BootcampReportModel report = new BootcampReportModel();
        report.setBootcampId(10L);
        report.setName("Bootcamp");
        report.setDescription("Description");
        report.setReleaseDate(LocalDate.of(2026, 1, 1));
        report.setDurationDays(30);
        report.setCapacities(capacities);
        report.setEnrolledPersons(enrolledPersons);
        return report;
    }

    private EnrolledPersonReportModel person(Long id, String name, String email) {
        return new EnrolledPersonReportModel(id, name, email);
    }
}
