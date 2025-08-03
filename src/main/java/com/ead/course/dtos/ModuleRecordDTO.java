package com.ead.course.dtos;

import jakarta.validation.constraints.NotBlank;

public record ModuleRecordDTO(
        @NotBlank(message="title is required")
        String title,
        @NotBlank(message="description is required")
        String description) {}
