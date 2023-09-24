package com.example.demo.service;

import com.example.demo.dto.AccountDto;
import com.example.demo.repository.AccountRepository;
import com.example.demo.urbanMobilityApplication;
import com.example.demo.entity.Account;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

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
    public void ShouldChangeFetchAccount_WhenUpdated(){
        // Arrange
        long accountId = account.getId();
        accountRepository.save(account);
        Account updatedAccount = accountRepository.findById(account.getId()).get();

        // Act
        accountService.updateAccountById(accountId, inputAccount);
        Account fetchUpdated = accountRepository.findById(accountId).get();

        // Assert
        assertThat(fetchUpdated.getId()).isEqualTo(updatedAccount.getId());
        assertThat(fetchUpdated.getUsername()).isNotEqualTo(updatedAccount.getUsername());
        assertThat(fetchUpdated.getEmail()).isNotEqualTo(updatedAccount.getEmail());
    }
    @Test
    @Transactional
    @Rollback(true)
    public void Should_BeEmpty_WhenDeleteByAccountById(){
        accountRepository.save(account);
        long accountId = account.getId();

        accountService.deleteAccountById(accountId);

        assertThat(accountRepository.findAll().size()).isEqualTo(0);

    }

    /*@AfterEach
    void cleanUp(){
        accountRepository.deleteAll();
    } */
}