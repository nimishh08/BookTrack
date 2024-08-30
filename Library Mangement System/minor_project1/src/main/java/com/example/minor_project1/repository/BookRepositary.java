package com.example.minor_project1.repository;

import com.example.minor_project1.model.Book;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.model.enums.Genre;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepositary extends JpaRepository<Book,Integer>{

    @Query("select b from Book b where b.name =:name and b.my_student is null")
     List<Book> findByNameAndMyStudentIsNull(String name);

     List<Book> findByName(String name);

     List<Book> findByGenre(Genre genre);
     @Transactional
     @Modifying
    @Query("update Book b set b.my_student = ?2 where b.id= ?1 and b.my_student is null")
     void assignBookToStudent(int bookId, Student student);


    @Transactional
    @Modifying
    @Query("update Book b set b.my_student = null where b.id= ?1")
    void unassignBookFromStudent(int bookId);
}
