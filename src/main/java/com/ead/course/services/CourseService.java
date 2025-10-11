package com.ead.course.services;

import com.ead.course.dtos.CourseRecordDTO;
import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import com.ead.course.specifications.SpecificationTemplate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(CourseModel courseModel);

    CourseModel save(CourseRecordDTO courseRecordDTO);

    boolean existsByName(@NotBlank String name);

    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable);


    Optional<CourseModel> findById(UUID courseId);

    CourseModel update(@Valid CourseRecordDTO courseRecordDTO, CourseModel courseModel);

    boolean existsByCourseAndUser(UUID courseId, @NotNull(message = "UserId is required") UUID userId);

    void saveSubscriptionInCourse(CourseModel courseModel, UserModel userModel);
}
