package com.example.minor_project1.model;

import com.example.minor_project1.model.enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"bookList"})
    private Author my_author;
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"bookList"})
    private Student my_student;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private Integer pages;
    @CreationTimestamp
    private Date createdon;
    @UpdateTimestamp
    private Date updatedon;
    @OneToMany(mappedBy = "book" ,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"book"})
    private List<Transaction> transactionList;
}
