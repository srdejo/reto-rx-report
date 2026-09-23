package com.pragma.report.domain.usecase;

import com.pragma.report.domain.api.IBootcampReportServicePort;
import com.pragma.report.domain.model.BootcampReportModel;
import com.pragma.report.domain.model.CapacityReportModel;
import com.pragma.report.domain.model.EnrolledPersonReportModel;
import com.pragma.report.domain.model.TechnologyReportModel;
import com.pragma.report.domain.spi.IBootcampReportPersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

public class BootcampReportUseCase implements IBootcampReportServicePort {

    private final IBootcampReportPersistencePort bootcampReportPersistencePort;

    public BootcampReportUseCase(IBootcampReportPersistencePort bootcampReportPersistencePort) {
        this.bootcampReportPersistencePort = bootcampReportPersistencePort;
    }

    @Override
    public Mono<BootcampReportModel> saveBootcampReport(BootcampReportModel bootcampReportModel) {
        List<CapacityReportModel> capacities = Objects.requireNonNullElse(bootcampReportModel.getCapacities(), List.of());
        List<EnrolledPersonReportModel> enrolledPersons =
                Objects.requireNonNullElse(bootcampReportModel.getEnrolledPersons(), List.of());

        bootcampReportModel.setCapacities(capacities);
        bootcampReportModel.setCapacityCount(capacities.size());
        bootcampReportModel.setTechnologyCount(countDistinctTechnologies(capacities));
        bootcampReportModel.setEnrolledPersons(enrolledPersons);
        bootcampReportModel.setEnrolledCount((long) enrolledPersons.size());
        bootcampReportModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("America/Bogota")));

        return bootcampReportPersistencePort.saveBootcampReport(bootcampReportModel);
    }

    @Override
    public Flux<BootcampReportModel> getAllBootcampReports() {
        return bootcampReportPersistencePort.getAllBootcampReports();
    }

    private int countDistinctTechnologies(List<CapacityReportModel> capacities) {
        return (int) capacities.stream()
                .map(CapacityReportModel::getTechnologies)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .map(TechnologyReportModel::getId)
                .distinct()
                .count();
    }
}
