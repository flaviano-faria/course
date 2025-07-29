package com.ead.course.controllers;

import com.ead.course.dtos.CourseRecordDTO;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Object> saveCourse(
            @RequestBody @Valid CourseRecordDTO courseRecordDTO){

        if(courseService.existsByName(courseRecordDTO.name())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("course name already exists");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.save(courseRecordDTO));
    }

    @GetMapping
    public ResponseEntity<List<CourseModel>> getAllCourses(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.findAll());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(
            @PathVariable(value = "courseId")UUID courseId){

        return ResponseEntity.status(HttpStatus.OK).body(courseService.findById(courseId).get());
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId){

        courseService.delete(courseService.findById(courseId).get());
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
