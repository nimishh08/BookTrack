package com.example.minor_project1.services;

import com.example.minor_project1.dto.CreateBookRequest;
import com.example.minor_project1.dto.SearchBookRequest;
import com.example.minor_project1.model.Author;
import com.example.minor_project1.model.Book;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.model.enums.Genre;
import com.example.minor_project1.repository.AuthorRepositary;
import com.example.minor_project1.repository.BookRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepositary bookRepositary;
    @Autowired
    AuthorService authorService;

    public Book create(CreateBookRequest createBookRequest){
        Book book=createBookRequest.to();
        Author author=this.authorService.createOrGet(book.getMy_author());
        book.setMy_author((author));
        return this.bookRepositary.save(book);
    }

    public Book delete(int bookId) {
        Book book=this.bookRepositary.findById(bookId).orElse(null);
        this.bookRepositary.deleteById(bookId);
        return book;
    }

    public void assignBookToStudent(Book book, Student student){
         bookRepositary.assignBookToStudent(book.getId(),student);
    }

    public void unassignBookFromStudent(Book book) {
        bookRepositary.unassignBookFromStudent(book.getId());
    }


    public List<Book> search(SearchBookRequest searchBookRequest) throws Exception {
//        boolean isValidRequest= searchBookRequest.validate();
//        if(!isValidRequest){
//            throw new Exception("Invalid Request");
//        }

        switch(searchBookRequest.getSearchKey()) {
            case "name": {
                if(searchBookRequest.isAvailable()){
                    return bookRepositary.findByNameAndMyStudentIsNull(searchBookRequest.getSearchValue());
                }
                return bookRepositary.findByName(searchBookRequest.getSearchValue());
            }
            case "genre": {
                return bookRepositary.findByGenre(Genre.valueOf(searchBookRequest.getSearchValue()));
            }
            case "id": {
                Book book = bookRepositary.findById(Integer.parseInt(searchBookRequest.getSearchValue())).orElse(null);
                return Arrays.asList(book);
            }
            default:{
                throw new Exception("Invalid Search Key");
            }
        }
    }



    public List<Book> get() {
        return bookRepositary.findAll();
    }

}
