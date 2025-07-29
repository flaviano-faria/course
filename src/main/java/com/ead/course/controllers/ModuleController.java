package com.ead.course.controllers;

import com.ead.course.dtos.ModuleRecordDTO;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ModuleController {

    final ModuleService moduleService;
    final CourseService courseService;

    public ModuleController(ModuleService moduleService, CourseService courseService) {
        this.moduleService = moduleService;
        this.courseService = courseService;
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> saveModule(
            @PathVariable(value = "courseId") UUID courseId,
            @RequestBody @Valid ModuleRecordDTO moduleRecordDTO){


        return ResponseEntity.status(HttpStatus.CREATED)
                .body( moduleService.save(
                        moduleRecordDTO, courseService.findById(courseId).get()));
    }
}
