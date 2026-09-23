package com.pragma.report.infrastructure.input.rest;

import com.pragma.report.application.dto.request.BootcampReportRequestDto;
import com.pragma.report.application.dto.response.BootcampReportResponseDto;
import com.pragma.report.application.handler.IBootcampReportHandler;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reports/bootcamps")
@RequiredArgsConstructor
public class BootcampReportRestController {

    private final IBootcampReportHandler bootcampReportHandler;

    @Operation(summary = "Create or update the report of a bootcamp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bootcamp report saved", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Mono<ResponseEntity<Void>> saveBootcampReport(
            @Valid @RequestBody BootcampReportRequestDto bootcampReportRequestDto) {
        return bootcampReportHandler.saveBootcampReport(bootcampReportRequestDto)
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }

    @Operation(summary = "Get all bootcamp reports")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All bootcamp reports returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BootcampReportResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Flux<BootcampReportResponseDto> getAllBootcampReports() {
        return bootcampReportHandler.getAllBootcampReports();
    }
}
