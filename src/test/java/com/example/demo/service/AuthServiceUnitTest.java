package com.example.demo.service;

import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceUnitTest {

    @Mock
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AuthService authService;

    private Account userAccount;

    @BeforeEach
    public void setup() {
        userAccount = Account.builder()
                .id(1L)
                .username("Isak")
                .role("User")
                .email("Isak@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
                .paymentHistory(0)
                .build();
    }

    @Test
    public void ShouldReturnValidMessage_WhenUserIsSupplier(){
        long accountId = 1L;
        given(accountService.getAccountById(accountId)).willReturn(Optional.of(userAccount));
        userAccount.setRole("supplier");

        authService.validSupplier(accountId);

        assertThat(authService.validSupplier(accountId)).isEqualTo("You are a supplier");
    }

    @Test
    public void shouldThrowException_IfUserAndNotSupplier(){
        given(accountService.getAccountById(userAccount.getId())).willReturn(Optional.of(userAccount));

        assertThrows(InvalidInputException.class,
                () -> authService.validSupplier(userAccount.getId()));
    }

}