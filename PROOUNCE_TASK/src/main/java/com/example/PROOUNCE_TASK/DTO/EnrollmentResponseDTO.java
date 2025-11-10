package com.example.PROOUNCE_TASK.DTO;

import com.example.PROOUNCE_TASK.Entity.Student;
import java.util.List;
import java.util.Set;

public record EnrollmentResponseDTO(
    Student student,
    List<Course> courses,
    Set<Long> missingCourseIds
) {}	