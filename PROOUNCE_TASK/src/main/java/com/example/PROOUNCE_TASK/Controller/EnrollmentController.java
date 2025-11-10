package com.example.PROOUNCE_TASK.Controller;

import com.example.PROOUNCE_TASK.DAO.StudentDAO;
import com.example.PROOUNCE_TASK.DTO.EnrollmentResponseDTO;
import com.example.PROOUNCE_TASK.RespoceStucture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private StudentDAO studentDAO; // DAO handles enrollment logic

    @Autowired
    private RespoceStucture<EnrollmentResponseDTO> enrollmentStructure;

    @Autowired
    private RespoceStucture<String> stringStructure;


    // Part C: POST /enrollments/{studentId}/courses/{courseId} [cite: 54]
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<RespoceStucture<String>> enrollStudent(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        
        studentDAO.enrollStudent(studentId, courseId);
        stringStructure.setSatuscode(HttpStatus.CREATED.value());
        stringStructure.setMessage("Student Enrolled Successfully");
        stringStructure.setData("Student " + studentId + " enrolled in course " + courseId);
        return new ResponseEntity<>(stringStructure, HttpStatus.CREATED);
    }

    // Part C: GET /enrollments/{studentId} [cite: 55]
    @GetMapping("/{studentId}")
    public ResponseEntity<RespoceStucture<EnrollmentResponseDTO>> getEnrollments(
            @PathVariable Long studentId) {
        
        EnrollmentResponseDTO dto = studentDAO.getEnrollmentDetails(studentId);
        enrollmentStructure.setSatuscode(HttpStatus.OK.value());
        enrollmentStructure.setMessage("Enrollment Details Found");
        enrollmentStructure.setData(dto);
        return new ResponseEntity<>(enrollmentStructure, HttpStatus.OK);
    }
}