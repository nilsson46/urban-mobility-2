package com.example.demo.service;

import com.example.demo.Exceptions.ResourceNotFoundException;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {urbanMobilityApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountServiceIntegrationTest {


    @Autowired
    AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    private Account account;
    private Account inputAccount;
    private Account anotherAccount;
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
                //.activeOrders(0)
                .build();

        inputAccount = Account.builder()
                .id(1L)
                .username("Isak")
                .role("User")
                .email("Isak@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
                .paymentHistory(0)
                //.activeOrders(0)
                .build();
        anotherAccount = Account.builder()
                .id(2L)
                .username("Viktor")
                .role("User")
                .email("Viktor@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
                .paymentHistory(0)
                //.activeOrders(0)
                .build();
    }



    @Test
    public void Should_CreateAndReturnAccountFromDatabase() {

        // Act
        Account savedAccount = accountService.createAccount(account);

        //Assert
        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getId()).isNotNull();

        Optional<Account> retrievedAccount = accountService.getAccountById(savedAccount.getId());
        assertThat(retrievedAccount).isNotNull();
        assertThat(retrievedAccount.get().getUsername()).isEqualTo("kuro");

    }

    @Test
    public void Should_CreateAndReturnTwoAccountFromDatabase() {

        //Act
        Account savedAccount = accountService.createAccount(account);
        Account anotherSavedAccount = accountService.createAccount(anotherAccount);

        //Assert
        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getId()).isNotNull();

        assertThat(anotherSavedAccount).isNotNull();
        assertThat(anotherSavedAccount.getId()).isNotNull();

        Optional<Account> retrievedAccount = accountService.getAccountById(savedAccount.getId());
        assertThat(retrievedAccount).isNotNull();
        assertThat(retrievedAccount.get().getUsername()).isEqualTo("kuro");

        Optional<Account> retrievedSecondAccount = accountService.getAccountById(anotherSavedAccount.getId());
        assertThat(retrievedSecondAccount).isNotNull();
        assertThat(retrievedSecondAccount.get().getUsername()).isEqualTo("Viktor");
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
    public void should_ThrowResourceNotFoundException_WhenUpdatingNonExistentAccount() {

        // Arrange
        Long nonExistentAccountId = 50L;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> accountService.updateAccountById(nonExistentAccountId, new Account())
        );
    }
    @Test
    public void Should_BeEmpty_WhenDeleteByAccountById(){
        //Arrange
        accountRepository.save(account);
        long accountId = account.getId();
        // Act
        accountService.deleteAccountById(accountId);
        //Assert
        assertThat(accountRepository.findAll().isEmpty());

    }
    @Test
    public void should_ThrowResourceNotFoundException_WhenDeletingNonExistentAccount() {

        // Arrange
        long nonExistentAccountId = 50L;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> accountService.deleteAccountById(nonExistentAccountId)
        );
    }

}