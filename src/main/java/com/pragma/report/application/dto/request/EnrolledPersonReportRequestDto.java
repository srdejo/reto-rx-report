package com.pragma.report.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrolledPersonReportRequestDto {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;
}
