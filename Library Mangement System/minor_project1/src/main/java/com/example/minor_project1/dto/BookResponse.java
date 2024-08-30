package com.example.minor_project1.dto;

import com.example.minor_project1.model.Author;
import com.example.minor_project1.model.Book;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.model.Transaction;
import com.example.minor_project1.model.enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private int id;

    private String name;

    private Author my_author;

    private Student my_student;

    private Genre genre;

    private Integer pages;

    private Date createdon;

    private Date updatedon;

    public static BookResponse from(Book b){
       return BookResponse.builder()
                .id(b.getId())
                .name(b.getName())
                .my_author(b.getMy_author())
                .my_student(b.getMy_student())
                .genre(b.getGenre())
                .pages(b.getPages())
                .createdon(b.getCreatedon())
                .updatedon(b.getUpdatedon())
                .build();
    }
}
