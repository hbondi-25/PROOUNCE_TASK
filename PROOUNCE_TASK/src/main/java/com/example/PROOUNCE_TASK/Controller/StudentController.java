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
        /*
         http://localhost:8080/students
         {
    "satuscode": 201,
    "message": "Student Created Successfully",
    "data": {
        "id": 2,
        "name": "Vardhan",
        "email": "vardhan@example.com"
    }
} 

to test 
input
{
    "name": "",
    "email": "abc@example.com"
}

                                               o/p
{
    "satuscode": 400,
    "message": "Validation Error",
    "data": {
        "timestamp": "2025-11-10T22:24:04.7847454",
        "status": 400,
        "error": "Validation Failed",
        "details": [
            "name: Name is required"
        ],
        "path": "/students"
    }
}

         */
        Student createdStudent = studentDAO.createStudent(student);
        structure.setSatuscode(HttpStatus.CREATED.value()); // Part B: returns 201 [cite: 43]
        structure.setMessage("Student Created Successfully");
        structure.setData(createdStudent);
        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    // Part B: GET /students/{id} [cite: 36]
    @GetMapping("/{id}")
    public ResponseEntity<RespoceStucture<Student>> getStudentById(@PathVariable Long id) {
    	/*
    	 
    	 http://localhost:8080/students/1
    	 
    	 {
    "satuscode": 200,
    "message": "Student Found",
    "data": {
        "id": 1,
        "name": "Harsha",
        "email": "harsha@example.com"
    }
} 	


Test This 
http://localhost:8080/students/999

{
    "satuscode": 404,
    "message": "Student not found with id: 999",
    "data": {
        "timestamp": "2025-11-10T22:31:07.0475483",
        "status": 404,
        "error": "Resource Not Found",
        "details": [
            "Student not found with id: 999"
        ],
        "path": "/students/999"
    }
}

    	 */
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
        
    	/*
    	 
    	 http://localhost:8080/students/1
    	             i/p
    	 {
    "name": "Harsha Vardhan",
    "email": "harsha.v@example.com"
}
    	 
    	              o/p
    	 {
    "satuscode": 200,
    "message": "Student Found",
    "data": {
        "id": 1,
        "name": "Harsha",
        "email": "harsha@example.com"
    }
    
    
}
    	 
    	 
    	 */
    	
        Student updatedStudent = studentDAO.updateStudent(id, studentDetails);
        structure.setSatuscode(HttpStatus.OK.value());
        structure.setMessage("Student Updated Successfully");
        structure.setData(updatedStudent);
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    // Part B: DELETE /students/{id} [cite: 38]
    @DeleteMapping("/{id}")
    public ResponseEntity<RespoceStucture<String>> deleteStudent(@PathVariable Long id) {
    	
    	/*
    	 
    	 http://localhost:8080/students/1
    	 
//    	 O/P
    	 
    	 {
    "satuscode": 200,
    "message": "Student Deleted Successfully",
    "data": "ID: 1 deleted."
}
    	 
    	 */
    	
        studentDAO.deleteStudent(id);
        stringStructure.setSatuscode(HttpStatus.OK.value());
        stringStructure.setMessage("Student Deleted Successfully");
        stringStructure.setData("ID: " + id + " deleted.");
        // Note: Returning 200 with body as per RespoceStructure, 
        // instead of 204 No Content
        return new ResponseEntity<>(stringStructure, HttpStatus.OK);
    }
}