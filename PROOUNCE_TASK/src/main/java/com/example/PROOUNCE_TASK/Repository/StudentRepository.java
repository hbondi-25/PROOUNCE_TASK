package com.example.PROOUNCE_TASK.Repository;

import com.example.PROOUNCE_TASK.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Note: @Repository is optional for JpaRepository, but good practice.
@Repository 
public interface StudentRepository extends JpaRepository<Student, Long> {
}