package com.example.demo.service;

import com.example.demo.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthServiceIntegrationTest {
    @Autowired
    AuthService authService;
    @Autowired
    AccountService accountService;

    private Account userAccount;
    private Account supplierAccount;

    @BeforeEach
    public void setup() {
        userAccount = Account.builder()
                .id(1L)
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
                .paymentHistory(0)
                //.activeOrders(0)
                .build();
        supplierAccount = Account.builder()
                .id(1L)
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
                .paymentHistory(0)
                //.activeOrders(0)
                .build();
    }

    @Test
    public void ShouldReturnValidSupplier_WhenAccountIsSupplier(){
        //Arrange
        accountService.createAccount(supplierAccount);

        //Act
        String result = authService.validSupplier(supplierAccount.getId());

        //Assert
        assertThat(result).isEqualTo("You are a supplier");
    }

}