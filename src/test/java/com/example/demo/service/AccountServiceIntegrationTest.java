package com.example.demo.service;

import com.example.demo.dto.AccountDto;
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
    private Account inputAccount;

    @BeforeEach
    public void setup(){
        account = Account.builder()
                .id(1L)
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
                .paymentHistory(0)
                .activeOrders(0)
                .build();

        inputAccount = Account.builder()
                .id(1L)
                .username("Isak")
                .role("User")
                .email("Isak@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
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

    public void Should_UpdateAccount_WhenUpdateIsMade(){

        //Arrange
        long accountId = account.getId();
        accountRepository.save(account);
        Account updatedAccount = accountRepository.findById(account.getId()).get();

        //Act
        accountService.updateAccount(accountId, inputAccount);
        Account fetchUpdate = accountRepository.findById(account.getId()).get();

        assertThat(fetchUpdate.getUsername()).isNotEqualTo(updatedAccount.getUsername());
        assertThat(fetchUpdate.getId()).isEqualTo(updatedAccount.getId());
        assertThat(fetchUpdate.getEmail()).isNotEqualTo(updatedAccount.getEmail());

    }
    @Test
    public void Should_BeEmpty_WhenDeleteByAccountById(){
        accountRepository.save(account);
        long accountId = account.getId();

        accountService.deleteAccountById(accountId);

        assertThat(accountRepository.findAll().size()).isEqualTo(0);

    }





}