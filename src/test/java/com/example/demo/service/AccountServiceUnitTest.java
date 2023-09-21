package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.repository.AccountRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AccountServiceUnitTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    public void setup(){
        account = Account.builder()
                .id(1L)
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .isPaymentConfirmed(true)
                .paymentHistory(0)
                .activeOrders(0)
                .build();
    }

    @Test
    public void should_ReturnUpdatedAccountDetails_When_AccountIsUpdated() {
        // Arrange
        String newUsername = "Simon";
        String newEmail = "simon@example.com";
        String newBankAccountNumber = "1234567890";

        // Mock
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Act
        Account updatedAccount = accountService.updateAccount(1L, newUsername, newEmail, newBankAccountNumber);

        // Assert
        assertEquals(newUsername, updatedAccount.getUsername());
        assertEquals(newEmail, updatedAccount.getEmail());
        assertEquals(newBankAccountNumber, updatedAccount.getBankAccountNumber());

        // Verify
        verify(accountRepository).save(account);
    }
    @Test
    public void Should_ReturnAccountDetails_When_CreateAccount(){

         //Arrange

        // Mock the behavior of accountRepository.save(account) to return the account
        given(accountRepository.save(account)).willReturn(account);

        // Act
        // Call the createAccount method to create an account and get the savedAccount
        Account savedAccount = accountService.createAccount(account);

        // Assert
        // Check that the savedAccount is not null, indicating a successful account creation
        assertThat(savedAccount).isNotNull();

        // Verify that the accountRepository's save method was called exactly once with the provided account
        verify(accountRepository, times(1)).save(account);

        // Verify that there were no other interactions with the accountRepository
        //verifyNoInteractions(accountRepository);

    }


    @Test
    public void Should_ReturnAccount_When_FindAccountById() {
        // Arrange
        Long accountId = 1L;

        // Mock the behavior of accountRepository.findById to return the account
        given(accountRepository.findById(accountId)).willReturn(Optional.of(account));

        // Act
        Account foundAccount = accountService.getAccountById(accountId);

        // Assert
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getId()).isEqualTo(accountId);

        // Verify that the accountRepository's findById method was called exactly once with the provided ID
        verify(accountRepository, times(1)).findById(accountId);
    }
    @Test
    public void Should_ThrowIllegalArgumentException_IfUsernameAlreadyExists(){
        //Arrange
        given(accountRepository.findByUsername(account.getUsername())).willReturn(account);

        //Act
        assertThrows(IllegalArgumentException.class,
                () -> accountService.createAccount(account));

        //Assert
        verify(accountRepository, times(1)).findByUsername(account.getUsername());
    }

    @Test
    public void Should_ThrowIllegalArgumentException_IfEmailAlreadyExists(){

    }


}