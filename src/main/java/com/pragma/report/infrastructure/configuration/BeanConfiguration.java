package com.pragma.report.infrastructure.configuration;

import com.pragma.report.domain.api.IReportServicePort;
import com.pragma.report.domain.spi.IReportPersistencePort;
import com.pragma.report.domain.usecase.ReportUseCase;
import com.pragma.report.infrastructure.out.mongo.adapter.ReportAdapter;
import com.pragma.report.infrastructure.out.mongo.mapper.IReportEntityMapper;
import com.pragma.report.infrastructure.out.mongo.repository.IReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IReportRepository reportRepository;
    private final IReportEntityMapper reportEntityMapper;

    @Bean
    public IReportPersistencePort reportPersistencePort() {
        return new ReportAdapter(reportRepository, reportEntityMapper);
    }

    @Bean
    public IReportServicePort reportServicePort() {
        return new ReportUseCase(reportPersistencePort());
    }
}
