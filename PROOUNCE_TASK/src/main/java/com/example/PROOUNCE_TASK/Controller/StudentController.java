package com.example.PROOUNCE_TASK.Controller;

import com.example.PROOUNCE_TASK.DAO.StudentDAO;
import com.example.PROOUNCE_TASK.Entity.Student;
import com.example.PROOUNCE_TASK.RespoceStucture;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentDAO studentDAO; // As requested, Controller autowires DAO

    @Autowired
    private RespoceStucture<Student> structure;

    @Autowired
    private RespoceStucture<String> stringStructure;

    // Part B: POST /students [cite: 35]
    @PostMapping
    public ResponseEntity<RespoceStucture<Student>> createStudent(
            @Valid @RequestBody Student student) { // Part E: @Valid [cite: 84]
        
        Student createdStudent = studentDAO.createStudent(student);
        structure.setSatuscode(HttpStatus.CREATED.value()); // Part B: returns 201 [cite: 43]
        structure.setMessage("Student Created Successfully");
        structure.setData(createdStudent);
        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    // Part B: GET /students/{id} [cite: 36]
    @GetMapping("/{id}")
    public ResponseEntity<RespoceStucture<Student>> getStudentById(@PathVariable Long id) {
        Student student = studentDAO.getStudentById(id);
        structure.setSatuscode(HttpStatus.OK.value());
        structure.setMessage("Student Found");
        structure.setData(student);
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    // Part B: PUT /students/{id} [cite: 37]
    @PutMapping("/{id}")
    public ResponseEntity<RespoceStucture<Student>> updateStudent(
            @PathVariable Long id, @Valid @RequestBody Student studentDetails) {
        
        Student updatedStudent = studentDAO.updateStudent(id, studentDetails);
        structure.setSatuscode(HttpStatus.OK.value());
        structure.setMessage("Student Updated Successfully");
        structure.setData(updatedStudent);
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    // Part B: DELETE /students/{id} [cite: 38]
    @DeleteMapping("/{id}")
    public ResponseEntity<RespoceStucture<String>> deleteStudent(@PathVariable Long id) {
        studentDAO.deleteStudent(id);
        stringStructure.setSatuscode(HttpStatus.OK.value());
        stringStructure.setMessage("Student Deleted Successfully");
        stringStructure.setData("ID: " + id + " deleted.");
        // Note: Returning 200 with body as per RespoceStructure, 
        // instead of 204 No Content
        return new ResponseEntity<>(stringStructure, HttpStatus.OK);
    }
}