package com.ead.course.services.impl;

import com.ead.course.dtos.LessonRecordDTO;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.services.LessonService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {

        this.lessonRepository = lessonRepository;
    }

    @Override
    public LessonModel save(LessonRecordDTO lessonRecordDTO, ModuleModel moduleModel) {
        var lessonModel = new LessonModel();
        BeanUtils.copyProperties(lessonRecordDTO, lessonModel);
        lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lessonModel.setModule(moduleModel);

        return lessonRepository.save(lessonModel);
    }

    @Override
    public List<LessonModel> findAllLessonsIntoModule(UUID moduleId) {
        return lessonRepository.findAllLessonsByModule(moduleId);
    }

    @Override
    public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        Optional<LessonModel> optionalLessonModel = lessonRepository.findLessonIntoModule(moduleId, lessonId);
        if(optionalLessonModel.isEmpty()) {

        }
        return optionalLessonModel;
    }

    @Override
    public void delete(LessonModel lessonModel) {
        lessonRepository.delete(lessonModel);
    }

    @Override
    public LessonModel update(LessonRecordDTO lessonRecordDTO, LessonModel lessonModel) {
        BeanUtils.copyProperties(lessonRecordDTO, lessonModel);
        return lessonRepository.save(lessonModel);
    }
}
