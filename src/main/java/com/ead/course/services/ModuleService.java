package com.ead.course.services;

import com.ead.course.dtos.ModuleRecordDTO;
import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {

    void delete(ModuleModel moduleModel);

    ModuleModel save(@Valid ModuleRecordDTO moduleRecordDTO, CourseModel byId);

    List<ModuleModel> findAllModulesIntoCourse(UUID courseId);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

    ModuleModel update(@Valid ModuleRecordDTO moduleRecordDTO, ModuleModel moduleModel);

    Optional<ModuleModel> findById(UUID moduleId);

    Page<ModuleModel> findAllModulesIntoCourse(Specification<ModuleModel> and, Pageable pageable);
}
