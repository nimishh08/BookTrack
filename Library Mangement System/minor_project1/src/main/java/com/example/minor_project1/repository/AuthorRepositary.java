package com.example.minor_project1.repository;

import com.example.minor_project1.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepositary extends JpaRepository<Author,Integer> {

    Author findByEmail(String email);
}
