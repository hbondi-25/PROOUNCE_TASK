package com.example.PROOUNCE_TASK.Controller;

import com.example.PROOUNCE_TASK.DAO.CourseDAO;
import com.example.PROOUNCE_TASK.DTO.Course;
import com.example.PROOUNCE_TASK.RespoceStucture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseDAO courseDAO; // As requested, Controller autowires DAO

    @Autowired
    private RespoceStucture<Course> courseStructure;

    @Autowired
    private RespoceStucture<List<Course>> courseListStructure;

    // Part A: GET /courses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RespoceStucture<Course>> getCourseById(@PathVariable Long id) {
        Course course = courseDAO.findCourseById(id);
        courseStructure.setSatuscode(HttpStatus.OK.value());
        courseStructure.setMessage("Course Found");
        courseStructure.setData(course);
        return new ResponseEntity<>(courseStructure, HttpStatus.OK);
    }

    // Part A: GET /courses?ids=101.102
    @GetMapping
    public ResponseEntity<RespoceStucture<List<Course>>> getCoursesByIds(
            @RequestParam("ids") String ids) {
        
        // As per PDF, delimiter is "." [cite: 25]
        List<Long> courseIds = Arrays.stream(ids.split("\\."))
                                     .map(Long::parseLong)
                                     .collect(Collectors.toList());
        
        List<Course> courses = courseDAO.findCoursesByIds(courseIds);
        courseListStructure.setSatuscode(HttpStatus.OK.value());
        courseListStructure.setMessage("Courses Found");
        courseListStructure.setData(courses);
        return new ResponseEntity<>(courseListStructure, HttpStatus.OK);
    }
}