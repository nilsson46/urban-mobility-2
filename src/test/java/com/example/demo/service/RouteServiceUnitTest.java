package com.example.demo.service;

import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.entity.Account;
import com.example.demo.entity.Route;
import com.example.demo.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteServiceUnitTest {
    @Mock
    private RouteRepository routeRepository;
    @Mock
    private AuthService authService;

    @InjectMocks
    private RouteService routeService;
    private AccountService accountService;

    private Route firstRoute;
    private Account supplierAccount;
    private Account userAccount;

    @BeforeEach
    public void setup(){
        supplierAccount = Account.builder()
                .id(1L)
                .username("SJ")
                .role("supplier")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
                .paymentHistory(0)
                .build();
        userAccount = Account.builder()
                .id(2L)
                .username("Isak")
                .role("User")
                .email("Isak@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
                .paymentHistory(0)
                .build();
        firstRoute = Route.builder()
                .id(3L)
                .departure("Kungsbacka")
                .arrival("Lindome")
                .supplier("SJ")
                .typeOfTransport("Train")
                .departureTime("2023-09-25T12:00:00")
                .arrivalTime("2023-09-25T12:15:00")
                .price("50.00")
                .discount("10%")
                .totalPrice("45.00")
                .build();
    }

    @Test
    public void testCreateRouteWithValidSupplier() {
        // Arrange
        long accountId = supplierAccount.getId();
        Route route = new Route();

        // Mock
        when(authService.validSupplier(accountId)).thenReturn("You are a supplier");
        when(routeRepository.save(route)).thenReturn(route);

        // Act
        Route result = routeService.createRoute(route, accountId);

        // Assert
        // Verify
        verify(authService).validSupplier(accountId);
        verify(routeRepository).save(route);

        // Assert
        assertEquals(route, result);
    }
    @Test
    public void testCreateRouteWithUserShouldThrowException() {
        // Arrange
        long accountId = userAccount.getId();
        Route route = new Route();

        // Mock
        when(authService.validSupplier(accountId)).thenThrow(InvalidInputException.class);

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> routeService.createRoute(route, accountId));
    }


    @Test
    void getAllRoutes() {
    }

    @Test
    void updateRoute() {
    }
}