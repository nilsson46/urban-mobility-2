package com.example.demo.service;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.dto.AccountDto;
import com.example.demo.entity.Account;
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
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AccountServiceUnitTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account fakeAccount;

    private Account inputAccount;

    @BeforeEach
    public void setup(){
        fakeAccount = Account.builder()
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
    public void Should_ReturnAccountDetails_When_CreateAccount(){

         //Arrange

        // Mock the behavior of accountRepository.save(account) to return the account
        given(accountRepository.save(fakeAccount)).willReturn(fakeAccount);

        // Act
        // Call the createAccount method to create an account and get the savedAccount
        Account savedAccount = accountService.createAccount(fakeAccount);

        // Assert
        // Check that the savedAccount is not null, indicating a successful account creation
        assertThat(savedAccount).isNotNull();

        // Verify that the accountRepository's save method was called exactly once with the provided account
        verify(accountRepository, times(1)).save(fakeAccount);

        // Verify that there were no other interactions with the accountRepository
        //verifyNoInteractions(accountRepository);

    }


    @Test
    public void Should_ReturnAccount_When_FindAccountById() {
        // Arrange
        Long accountId = 1L;

        // Mock the behavior of accountRepository.findById to return the account
        given(accountRepository.findById(accountId)).willReturn(Optional.of(fakeAccount));

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
        given(accountRepository.findByUsername(fakeAccount.getUsername())).willReturn(fakeAccount);

        //Act
        assertThrows(InvalidInputException.class,
                () -> accountService.createAccount(fakeAccount));

        //Assert
        verify(accountRepository, times(1)).findByUsername(fakeAccount.getUsername());
    }

    @Test
    public void Should_ThrowIllegalArgumentException_IfEmailAlreadyExists(){

        //Arrange
        given(accountRepository.findByEmail(fakeAccount.getEmail())).willReturn(fakeAccount);

        //Act
        assertThrows(InvalidInputException.class,
                () -> accountService.createAccount(fakeAccount));

        //Assert
        verify(accountRepository, times(1)).findByEmail(fakeAccount.getEmail());
    }

    /*@Test
    public void should_ReturnUpdatedAccountDetails_When_AccountIsUpdated() {
        // Arrange
        String newUsername = "Simon";
        String newEmail = "simon@example.com";
        String newBankAccountNumber = "1234567890";

        AccountDto updateRequest = new AccountDto();
        updateRequest.setUsername(newUsername);
        updateRequest.setEmail(newEmail);
        updateRequest.setBankAccountNumber(newBankAccountNumber);

        // Mock
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Act
        Account updatedAccount = accountService.updateAccount(1L, updateRequest);

        // Assert
        assertEquals(newUsername, updatedAccount.getUsername());
        assertEquals(newEmail, updatedAccount.getEmail());
        assertEquals(newBankAccountNumber, updatedAccount.getBankAccountNumber());

        // Verify
        verify(accountRepository).save(account);
    } */
    @Test
    public void testUpdateAccount() {
        // Arrange
        long accountId = fakeAccount.getId();
        given(accountRepository.existsById(accountId)).willReturn(true);
        given(accountRepository.save(inputAccount)).willReturn(inputAccount);

        // Act
        Account updatedAccount = accountService.updateAccountById(accountId, inputAccount);

        // Assert
        assertThat(updatedAccount.getUsername()).isEqualTo(inputAccount.getUsername());
        assertThat(updatedAccount.getId()).isEqualTo(inputAccount.getId());
        assertThat(updatedAccount.getEmail()).isEqualTo(inputAccount.getEmail());

    }
    @Test
    public void testThrow(){
        long accountId = fakeAccount.getId();

        given(accountRepository.existsById(accountId)).willReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> accountService.updateAccountById(accountId, fakeAccount));
    }

    @Test
    public void Should_DeleteAccount_WhenPassingValidId(){
        long accountId = fakeAccount.getId();
        given(accountRepository.existsById(accountId)).willReturn(true);
        willDoNothing().given(accountRepository).deleteById(accountId);

        accountService.deleteAccountById(accountId);

        verify(accountRepository, times(1)).deleteById(accountId);

    }
    @Test
    public void ShouldT_ThrowException_WhenPassingInvalidId(){
        // Arrange
        long accountId = 2L;
        given(accountRepository.existsById(accountId)).willReturn(false);

        // Act
        assertThrows(ResourceNotFoundException.class,
                () -> accountService.deleteAccountById(accountId));
    }

    }

