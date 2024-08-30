package com.example.minor_project1.repository;

import com.example.minor_project1.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepositary extends JpaRepository<Student,Integer> {
}
