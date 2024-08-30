package com.example.minor_project1.services;

import com.example.minor_project1.dto.SearchBookRequest;
import com.example.minor_project1.model.Book;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.model.Transaction;
import com.example.minor_project1.model.enums.TransactionStatus;
import com.example.minor_project1.model.enums.TransactionType;
import com.example.minor_project1.repository.TransactionRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    @Value("${student.issue.max_books}")
    private int maxBooksForIssuance;

    @Value("${student.issue.number_of_days}")
    private int numberOfDaysForIssuance;
    @Autowired
    TransactionRepositary transactionRepositary;



    public String issueTxn(String bookName,int studentID) throws Exception {

        List<Book> bookList=new ArrayList<>();
        try {
          bookList = bookService.search(SearchBookRequest.builder()
                            .searchKey("name")
                            .searchValue(bookName)
                            .operator("=")
                            .available(true)
                            .build());
        } catch (Exception e) {
            throw new Exception("Book Not Found");
        }

        Student student=studentService.get(studentID);

        if(student.getBookList() != null && student.getBookList().size() >=maxBooksForIssuance){
                throw new Exception("Book limit reached");
        }

        if(bookList.isEmpty()){
            throw new Exception("Not able to find any book in the libary");
        }

        Book book=bookList.get(0);

        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .student(student)
                .book(book)
                .transactionStatus(TransactionStatus.PENDING)
                .build();
        transaction=transactionRepositary.save(transaction);

        try {
            book.setMy_student(student);
            bookService.assignBookToStudent(book,student);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);

        }finally {
            return transactionRepositary.save(transaction).getExternalTxnId();
        }
    }

    public String returnTxn(int bookId,int studentID) throws Exception {

        Book book;
        try {
            book = bookService.search(
                    SearchBookRequest.builder()
                            .searchKey("id")
                            .searchValue(String.valueOf(bookId))
                            .build()
            ).get(0);
        }catch (Exception e){
            throw new Exception("not able to fetch book");
        }
        if(book.getMy_student() == null || book.getMy_student().getId() != studentID){
            throw new Exception("Book is not assigned to this student");
        }


        Student student = studentService.get(studentID);

        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .student(student)
                .book(book)
                .transactionStatus(TransactionStatus.PENDING)
                .build();
        transaction=transactionRepositary.save(transaction);

        Transaction returnTransaction= transactionRepositary.findTopByStudentAndBookAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(student,book,TransactionType.ISSUE,TransactionStatus.SUCCESS);

        long issueTxninMilli= returnTransaction.getTransactionTime().getTime();

        long currentTimeMillis=System.currentTimeMillis();

        long timeDiffrenceinMillis= currentTimeMillis - issueTxninMilli;

        long timeDiffInDays= TimeUnit.DAYS.convert(timeDiffrenceinMillis,TimeUnit.MILLISECONDS);


        Double fine=0.0;
        if(timeDiffInDays>numberOfDaysForIssuance){
            fine = (timeDiffInDays-numberOfDaysForIssuance)*1.0;
        }
        try {

            book.setMy_student(null);
            bookService.unassignBookFromStudent(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        }finally {
            transaction.setFine(fine);
            return transactionRepositary.save(transaction).getExternalTxnId();
        }
    }
}
