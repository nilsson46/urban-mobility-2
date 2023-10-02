package com.example.demo.service;

import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceUnitTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private AccountService accountService;

    @Mock
    private RouteService routeService;
    private Account validPaymentAccount;

    private Account inValidPaymentAccount;
    @BeforeEach
    public void setup(){
        validPaymentAccount = Account.builder()
                .id(1L)
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
                .paymentHistory(0)
                //.activeOrders(0)
                .build();
        inValidPaymentAccount = Account.builder()
                .id(1L)
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(false)
                .paymentHistory(0)
                //.activeOrders(0)
                .build();
    }

    @Test
    void should_ReturnOrder_WhenPaymentIsValid() {
        //Arrange
        long routeId = 1L;
        long accountId = validPaymentAccount.getId();
        when(accountService.getAccountById(accountId)).thenReturn(Optional.of(validPaymentAccount));
        //Act
        orderService.makeAOrder(routeId, accountId);
        //Assert
        assertEquals(1, validPaymentAccount.getPaymentHistory());
    }
    @Test
    void should_ThrowInvalidInputException_WhenPaymentIsInvalid() {
        // Arrange
        long routeId = 1L;
        long accountId = 1L;
        when(accountService.getAccountById(accountId)).thenReturn(Optional.of(inValidPaymentAccount));

        // Act and Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> orderService.makeAOrder(routeId, accountId));
        assertEquals("You dont have a valid payment", exception.getMessage());

        // Verify that updateRouteById was not called
        verify(routeService, never()).updateRouteById(anyLong(), anyLong());
    }

    @Test
    void should_deleteOrder_WhenOrderExists() {
        //Arrange
        long routeId = 1L;
        long accountId = 1L;
        validPaymentAccount.setPaymentHistory(1);
        when(accountService.getAccountById(anyLong())).thenReturn(Optional.of(validPaymentAccount));
        //Act
        orderService.deleteOrder(accountId, routeId);
        // Assert
        assertEquals(0, validPaymentAccount.getPaymentHistory());
    }
}