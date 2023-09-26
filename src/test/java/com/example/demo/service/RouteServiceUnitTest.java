package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Route;
import com.example.demo.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RouteServiceUnitTest {
    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;
    private AccountService accountService;

    private Route firstRoute;
    private Account fakeAccount;
    private Account userAccount;

    @BeforeEach
    public void setup(){
        fakeAccount = Account.builder()
                .id(1L)
                .username("SJ")
                .role("supplier")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
                .paymentHistory(0)
                //.activeOrders(0)
                .build();
        userAccount = Account.builder()
                .id(2L)
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
    void createRoute() {
    }

    @Test
    void getAllRoutes() {
    }

    @Test
    void updateRoute() {
    }
}