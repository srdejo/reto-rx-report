package com.pragma.report.infrastructure;

import com.pragma.report.domain.model.BootcampReportModel;
import com.pragma.report.infrastructure.out.mongo.adapter.BootcampReportAdapter;
import com.pragma.report.infrastructure.out.mongo.entity.BootcampReportEntity;
import com.pragma.report.infrastructure.out.mongo.mapper.IBootcampReportEntityMapper;
import com.pragma.report.infrastructure.out.mongo.repository.IBootcampReportRepository;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BootcampReportAdapterTest {

    private final IBootcampReportRepository repository = mock(IBootcampReportRepository.class);
    private final ReactiveMongoTemplate mongoTemplate = mock(ReactiveMongoTemplate.class);
    private final IBootcampReportEntityMapper mapper = Mappers.getMapper(IBootcampReportEntityMapper.class);
    private final BootcampReportAdapter adapter = new BootcampReportAdapter(repository, mapper, mongoTemplate);

    @Test
    void savesWhenSnapshotIsNewer() {
        BootcampReportModel incoming = report(2L, LocalDateTime.of(2026, 1, 1, 10, 0, 1));
        when(mongoTemplate.findAndReplace(any(Query.class), any(BootcampReportEntity.class),
                any(FindAndReplaceOptions.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(1)));

        StepVerifier.create(adapter.saveBootcampReport(incoming))
                .assertNext(saved -> assertThat(saved.getEnrolledCount()).isEqualTo(2L))
                .verifyComplete();
    }

    @Test
    void keepsStoredReportWhenSnapshotIsStale() {
        BootcampReportModel stale = report(1L, LocalDateTime.of(2026, 1, 1, 10, 0, 0));
        BootcampReportEntity stored = mapper.toEntity(report(2L, LocalDateTime.of(2026, 1, 1, 10, 0, 1)));
        when(mongoTemplate.findAndReplace(any(Query.class), any(BootcampReportEntity.class),
                any(FindAndReplaceOptions.class)))
                .thenReturn(Mono.error(new DuplicateKeyException("E11000 duplicate key")));
        when(repository.findById(eq(10L))).thenReturn(Mono.just(stored));

        StepVerifier.create(adapter.saveBootcampReport(stale))
                .assertNext(saved -> assertThat(saved.getEnrolledCount()).isEqualTo(2L))
                .verifyComplete();
    }

    private BootcampReportModel report(Long enrolledCount, LocalDateTime snapshotAt) {
        BootcampReportModel report = new BootcampReportModel();
        report.setBootcampId(10L);
        report.setName("Bootcamp");
        report.setEnrolledCount(enrolledCount);
        report.setSnapshotAt(snapshotAt);
        return report;
    }
}
