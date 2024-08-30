package com.example.minor_project1.controller;


import com.example.minor_project1.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/issue")
    public String issueTxn(@RequestParam("name") String name,
                             @RequestParam("studentId") int studentId
                            ) throws Exception {
        return transactionService.issueTxn(name,studentId);
    }
    @PostMapping("/transaction/return")
    public String returnTxn(@RequestParam("bookId") int bookId,
                            @RequestParam("studentId") int studentId) throws Exception {
        return transactionService.returnTxn(bookId,studentId);
    }
}
