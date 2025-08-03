package com.ead.course.dtos;

import jakarta.validation.constraints.NotBlank;

public record LessonRecordDTO(@NotBlank(message="title is required")
                              String title,
                              @NotBlank(message="description is required")
                              String description,
                              @NotBlank(message="videoUrl is required")
                              String videoUrl) {
}
