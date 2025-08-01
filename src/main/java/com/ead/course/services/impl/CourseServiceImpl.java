package com.ead.course.services.impl;

import com.ead.course.dtos.CourseRecordDTO;
import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    final CourseRepository courseRepository;
    final ModuleRepository moduleRepository;
    final LessonRepository lessonRepository;

    public CourseServiceImpl(CourseRepository courseRepository,
                             ModuleRepository moduleRepository,
                             LessonRepository lessonRepository) {

        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public void delete(CourseModel courseModel) {
        List<ModuleModel> moduleModelList =
                moduleRepository.findModulesByCourse(courseModel.getCourseId());
        if(!moduleModelList.isEmpty()) {
            for(ModuleModel moduleModel : moduleModelList) {
                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsByModule(moduleModel.getModuleId());
                if(!lessonModelList.isEmpty()) {
                    lessonRepository.deleteAll(lessonModelList);
                }
            }
            moduleRepository.deleteAll(moduleModelList);
        }
        courseRepository.delete(courseModel);
    }

    @Override
    public CourseModel save(CourseRecordDTO courseRecordDTO) {
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseRecordDTO, courseModel);
        courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return courseRepository.save(courseModel);
    }

    @Override
    public boolean existsByName(String name) {
        return courseRepository.existsByName(name);
    }

    @Override
    public List<CourseModel> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {

        Optional<CourseModel> optionalCourseModel = courseRepository.findById(courseId);
        if(optionalCourseModel.isEmpty()) {

        }
        return courseRepository.findById(courseId);
    }

    @Override
    public CourseModel update(CourseRecordDTO courseRecordDTO, CourseModel courseModel) {
        BeanUtils.copyProperties(courseRecordDTO, courseModel);
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(courseModel);
    }
}
