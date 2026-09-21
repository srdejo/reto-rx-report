package com.pragma.report.infrastructure.input.rest;

import com.pragma.report.application.dto.request.ReportRequestDto;
import com.pragma.report.application.dto.response.ReportResponseDto;
import com.pragma.report.application.handler.IReportHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportRestController {

    private final IReportHandler reportHandler;

    @Operation(summary = "Add a new report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Report created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @PostMapping("/")
    public Mono<ResponseEntity<Void>> saveReport(@Valid @RequestBody ReportRequestDto reportRequestDto) {
        return reportHandler.saveReport(reportRequestDto)
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }

    @Operation(summary = "Get all reports")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All reports returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ReportResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/")
    public Flux<ReportResponseDto> getAllReports() {
        return reportHandler.getAllReports();
    }
}
