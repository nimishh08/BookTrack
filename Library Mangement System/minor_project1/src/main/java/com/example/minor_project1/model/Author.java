package com.example.minor_project1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    private String country;
    @CreationTimestamp
    private Date addedOn;
    @OneToMany(mappedBy = "my_author")//we are creating bidirectional realtion here
    @JsonIgnoreProperties({"my_author"})
    private List<Book> bookList;
}
