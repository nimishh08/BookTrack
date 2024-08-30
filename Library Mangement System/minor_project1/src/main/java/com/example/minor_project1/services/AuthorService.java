package com.example.minor_project1.services;

import com.example.minor_project1.model.Author;
import com.example.minor_project1.repository.AuthorRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepositary authorRepositary;
    public Author createOrGet(Author author){
        Author authorFromDB=this.authorRepositary.findByEmail(author.getEmail());
        if(authorFromDB != null){
            return  authorFromDB;
        }
        return this.authorRepositary.save(author);
    }
}
