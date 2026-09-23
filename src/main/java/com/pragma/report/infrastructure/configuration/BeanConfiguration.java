package com.pragma.report.infrastructure.configuration;

import com.pragma.report.domain.api.IBootcampReportServicePort;
import com.pragma.report.domain.spi.IBootcampReportPersistencePort;
import com.pragma.report.domain.usecase.BootcampReportUseCase;
import com.pragma.report.infrastructure.out.mongo.adapter.BootcampReportAdapter;
import com.pragma.report.infrastructure.out.mongo.mapper.IBootcampReportEntityMapper;
import com.pragma.report.infrastructure.out.mongo.repository.IBootcampReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IBootcampReportRepository bootcampReportRepository;
    private final IBootcampReportEntityMapper bootcampReportEntityMapper;

    @Bean
    public IBootcampReportPersistencePort bootcampReportPersistencePort() {
        return new BootcampReportAdapter(bootcampReportRepository, bootcampReportEntityMapper);
    }

    @Bean
    public IBootcampReportServicePort bootcampReportServicePort() {
        return new BootcampReportUseCase(bootcampReportPersistencePort());
    }
}
