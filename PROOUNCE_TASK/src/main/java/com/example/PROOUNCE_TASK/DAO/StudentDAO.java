package com.example.PROOUNCE_TASK.DAO;

import com.example.PROOUNCE_TASK.DTO.Course;
import com.example.PROOUNCE_TASK.DTO.EnrollmentResponseDTO;
import com.example.PROOUNCE_TASK.Entity.Student;
import com.example.PROOUNCE_TASK.Exception.ResourceNotFoundException;
import com.example.PROOUNCE_TASK.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository // Marking as a Spring Bean
public class StudentDAO {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseDAO courseDAO; // For Part C "inter-service" call

    // Part C: Maintain enrollment in memory
    private final Map<Long, Set<Long>> enrollmentStore = new ConcurrentHashMap<>();

    // --- Part B: Student CRUD ---

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        // Part B: returns record or 404
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student existingStudent = getStudentById(id); // Re-uses the 404 check
        existingStudent.setName(studentDetails.getName());
        existingStudent.setEmail(studentDetails.getEmail());
        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(Long id) {
        Student existingStudent = getStudentById(id); // Ensures student exists
        studentRepository.delete(existingStudent);
    }

    // --- Part C: Enrollment Logic ---

    /**
     * Part C: Enroll endpoint stores mapping
     */
    public void enrollStudent(Long studentId, Long courseId) {
        // First, check if student exists. Throws 404 if not.
        getStudentById(studentId); 
        
        // Store in the map
        enrollmentStore.computeIfAbsent(studentId, k -> new HashSet<>()).add(courseId);
    }

    /**
     * Part C: Get enrollment details
     */
    public EnrollmentResponseDTO getEnrollmentDetails(Long studentId) {
        // 1. Get Student
        Student student = getStudentById(studentId);

        // 2. Get enrolled course IDs from memory
        Set<Long> enrolledCourseIds = enrollmentStore.getOrDefault(studentId, Collections.emptySet());

        List<Course> foundCourses = new ArrayList<>();
        Set<Long> missingCourseIds = new HashSet<>();

        if (!enrolledCourseIds.isEmpty()) {
            
            // 3. "Inter-service call" is now a direct DAO call [cite: 61]
            foundCourses = courseDAO.findCoursesByIds(new ArrayList<>(enrolledCourseIds));

            // 4. Find missing course IDs [cite: 56]
            Set<Long> foundIds = foundCourses.stream()
                                            .map(Course::id)
                                            .collect(Collectors.toSet());

            missingCourseIds = enrolledCourseIds.stream()
                                            .filter(id -> !foundIds.contains(id))
                                            .collect(Collectors.toSet());
        }

        // 5. Build and return the response DTO [cite: 55]
        return new EnrollmentResponseDTO(student, foundCourses, missingCourseIds);
    }
}