package com.ead.course.controllers;

import com.ead.course.dtos.CourseRecordDTO;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.SpecificationTemplate;
import com.ead.course.validations.CourseValidator;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {
    Logger logger = LogManager.getLogger(CourseController.class);
    final CourseService courseService;
    final CourseValidator courseValidator;

    public CourseController(CourseService courseService, CourseValidator courseValidator) {
        this.courseService = courseService;
        this.courseValidator = courseValidator;
    }

    @PostMapping
    public ResponseEntity<Object> saveCourse(
            @RequestBody @Valid CourseRecordDTO courseRecordDTO){

        if(courseService.existsByName(courseRecordDTO.name())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("course name already exists");
        }
        logger.debug("POST saveCourse received userRecordDTO: {}", courseRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.save(courseRecordDTO));
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourses(
            SpecificationTemplate.CourseSpec spec, Pageable pageable,
            @RequestParam(required = false) UUID userId){

        Page<CourseModel> courseModelPage =  courseService.findAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.findAll(spec, pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(
            @PathVariable(value = "courseId")UUID courseId){
        logger.debug("GET getOneCourse received courseId: {}", courseId);
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findById(courseId).get());
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId){

        courseService.delete(courseService.findById(courseId).get());
        logger.debug("DELETE deleteCourse received courseId: {}", courseId);
        return ResponseEntity.status(HttpStatus.OK).body("Course successfully deleted");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(
            @PathVariable(value = "courseId") UUID courseId, @RequestBody @Valid CourseRecordDTO courseRecordDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.update(
                        courseRecordDTO, courseService.findById(courseId).get()));
    }

    @RestController
    public static class ModuleController {

        final ModuleService moduleService;
        final CourseService courseService;

        public ModuleController(ModuleService moduleService, CourseService courseService) {
            this.moduleService = moduleService;
            this.courseService = courseService;
        }


    }
}
