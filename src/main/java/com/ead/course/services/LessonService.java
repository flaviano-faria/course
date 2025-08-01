package com.ead.course.services;


import com.ead.course.dtos.LessonRecordDTO;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import jakarta.validation.Valid;

public interface LessonService {
    LessonModel save(@Valid LessonRecordDTO lessonRecordDTO, ModuleModel moduleModel);
}
