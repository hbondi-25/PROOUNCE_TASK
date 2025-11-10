package com.example.PROOUNCE_TASK.DAO;

import com.example.PROOUNCE_TASK.DTO.Course;
import com.example.PROOUNCE_TASK.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository // Marking as a Spring Bean
public class CourseDAO {

    // Part A: Use an in-memory map
    private static final Map<Long, Course> courseStore = new ConcurrentHashMap<>();

    // Part A: Preload 4-5 course records
    static {
        courseStore.put(101L, new Course(101L, "Java Fundamentals", "Dr. Smith"));
        courseStore.put(102L, new Course(102L, "Spring Boot Microservices", "Prof. Johnson"));
        courseStore.put(103L, new Course(103L, "Database Systems", "Dr. Lee"));
        courseStore.put(104L, new Course(104L, "React Basics", "Ms. Brown"));
        courseStore.put(105L, new Course(105L, "Cloud Computing", "Mr. Davis"));
    }

    /**
     * Part A: Get course by ID
     */
    public Course findCourseById(Long id) {
        Course course = courseStore.get(id);
        if (course == null) {
            // Part A: Return 404 if course not found
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        return course;
    }

    /**
     * Part A: Get multiple courses by IDs
     */
    public List<Course> findCoursesByIds(List<Long> ids) {
        return ids.stream()
                .map(courseStore::get)
                .filter(java.util.Objects::nonNull) // Filter out nulls for non-existent IDs
                .collect(Collectors.toList());
    }
}