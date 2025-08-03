package com.ead.course.dtos;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CourseRecordDTO(
                              @NotBlank(message="name is required")
                              String name,
                              @NotBlank(message="description is required")
                              String description,
                              @NotBlank(message="courseStatus is required")
                              CourseStatus courseStatus,
                              @NotBlank(message="courseLevel is required")
                              CourseLevel courseLevel,
                              @NotBlank(message="userInstructor is required")
                              UUID userInstructor,

                              String imageUrl) {}
