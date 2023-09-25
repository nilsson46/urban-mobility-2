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
    }



    @Test
    public void Should_ReturnAccountDetails_When_CreateAccount(){

         //Arrange

        given(accountRepository.save(fakeAccount)).willReturn(fakeAccount);

        // Act

        Account savedAccount = accountService.createAccount(fakeAccount);

        // Assert
        assertThat(savedAccount).isNotNull();

        verify(accountRepository, times(1)).save(fakeAccount);


    }


    @Test
    public void Should_ReturnAccount_When_FindAccountById() {
        // Arrange
        Long accountId = 1L;

        //Mock
        given(accountRepository.findById(accountId)).willReturn(Optional.of(fakeAccount));

        // Act
        Account foundAccount = accountService.getAccountById(accountId);

        // Assert
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getId()).isEqualTo(accountId);

        //Verrify
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
    /*@Test
    public void testThrow(){

       //Arrange
        long accountId = fakeAccount.getId();
        given(accountRepository.existsById(accountId)).willReturn(false);

        //Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> accountService.updateAccountById(accountId, fakeAccount));
    } */

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

