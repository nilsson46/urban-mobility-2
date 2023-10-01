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
    void makeAOrder() {
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
    void makeAInvalidOrder() {
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
    void testDeleteOrder() {
        long routeId = 1L;
        long accountId = 1L;
        // Set the payment history to 1 to simulate a valid payment
        validPaymentAccount.setPaymentHistory(1);

        // Mock the behavior of accountService to return the valid payment account
        when(accountService.getAccountById(anyLong())).thenReturn(Optional.of(validPaymentAccount));

        // Call the deleteOrder method with accountId and routeId
        orderService.deleteOrder(accountId, routeId); // Pass accountId and routeId

        // Assert that the payment history is updated to 0
        assertEquals(0, validPaymentAccount.getPaymentHistory());
    }
}