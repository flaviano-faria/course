package com.ead.course.dtos;

import jakarta.validation.constraints.NotBlank;

public record LessonRecordDTO(@NotBlank
                              String title,
                              @NotBlank
                              String description,
                              @NotBlank
                              String videoUrl) {
}
