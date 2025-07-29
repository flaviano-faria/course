package com.ead.course.services;

import com.ead.course.dtos.ModuleRecordDTO;
import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import jakarta.validation.Valid;

import java.util.Optional;

public interface ModuleService {

    void delete(ModuleModel moduleModel);

    ModuleModel save(@Valid ModuleRecordDTO moduleRecordDTO, CourseModel byId);
}
