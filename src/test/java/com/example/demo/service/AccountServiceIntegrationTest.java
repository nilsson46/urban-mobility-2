package com.example.demo.service;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.dto.AccountUpdateRequest;
import com.example.demo.repository.AccountRepository;
import com.example.demo.urbanMobilityApplication;
import com.example.demo.entity.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {urbanMobilityApplication.class})
class AccountServiceIntegrationTest {


    @Autowired
    AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void setup(){
        account = Account.builder()
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .isPaymentConfirmed(true)
                .paymentHistory(0)
                .activeOrders(0)
                .build();
    }

    @AfterEach
    void cleanUp(){
        accountRepository.deleteAll();
    }

    @Test
    public void Should_CreateAndReturnAccountFromDatabase() {
        Account savedAccount = accountService.createAccount(account);

        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getId()).isNotNull();

        Account retrievedAccount = accountService.getAccountById(savedAccount.getId());
        assertThat(retrievedAccount).isNotNull();
        assertThat(retrievedAccount.getUsername()).isEqualTo("kuro");

    }

    @Test
    public void should_UpdateAccount_AndReturnUpdatedAccountDetails_FromDatabase(){

        // Create the initial Account in the database
        Account createdAccount = accountService.createAccount(account);
        Long accountId = createdAccount.getId();

        // Update the Account's fields using the DTO
        AccountUpdateRequest updateRequest = new AccountUpdateRequest();
        updateRequest.setUsername("Simon");
        updateRequest.setEmail("simon@example.com");
        updateRequest.setBankAccountNumber("87654321");

        Account updatedAccount = accountService.updateAccount(accountId, updateRequest);

        // Assertions
        assertThat(updatedAccount).isNotNull();
        assertEquals(accountId, updatedAccount.getId());
        assertEquals(updateRequest.getUsername(), updatedAccount.getUsername());
        assertEquals(updateRequest.getEmail(), updatedAccount.getEmail());
        assertEquals(updateRequest.getBankAccountNumber(), updatedAccount.getBankAccountNumber());
    }
    @Test
    public void Should_BeEmpty_WhenDeleteByAccountById(){
        accountRepository.save(account);
        long accountId = account.getId();

        accountService.deleteAccountById(accountId);

        assertThat(accountRepository.findAll().size()).isEqualTo(0);

    }





}