package com.example.minor_project1;

import com.example.minor_project1.services.TransactionService;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;
    @Test
    public void issueTxn_test() throws Exception {

        transactionService.issueTxn("ABC",1);
    }

}
