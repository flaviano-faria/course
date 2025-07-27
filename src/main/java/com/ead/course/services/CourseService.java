package com.ead.course.services;

import com.ead.course.dtos.CourseRecordDTO;
import com.ead.course.models.CourseModel;
import jakarta.validation.constraints.NotBlank;

public interface CourseService {

    void delete(CourseModel courseModel);

    CourseModel save(CourseRecordDTO courseRecordDTO);

    boolean existsByName(@NotBlank String name);
}
