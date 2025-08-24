package com.ead.course.services.impl;

import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.services.CourseUserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    final CourseUserRepository repository;
    private final CourseRepository courseRepository;
    private final CourseUserRepository courseUserRepository;

    public CourseUserServiceImpl(CourseUserRepository repository, CourseRepository courseRepository, CourseUserRepository courseUserRepository) {

        this.repository = repository;
        this.courseRepository = courseRepository;
        this.courseUserRepository = courseUserRepository;
    }

    @Override
    public boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId) {
        return repository.existsByCourseAndUserId(courseModel, userId);
    }

    @Override
    public CourseUserModel saveAndSendSubscriptionUserInCourse(
            CourseUserModel courseUserModel) {
        courseUserModel = courseUserRepository.save(courseUserModel);
        return courseUserModel;
    }
}
