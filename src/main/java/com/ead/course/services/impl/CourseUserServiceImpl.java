package com.ead.course.services.impl;

import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.services.CourseUserService;
import org.springframework.stereotype.Service;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    final CourseUserRepository repository;

    public CourseUserServiceImpl(CourseUserRepository repository) {
        this.repository = repository;
    }
}
