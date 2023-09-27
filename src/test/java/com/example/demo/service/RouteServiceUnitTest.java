package com.example.demo.service;
import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.Exceptions.ResourceNotFoundException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

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

    private Account fakeAccount;

    private Route secondRoute;
    private Account supplierAccount;

    private Account userAccount;

    @BeforeEach
    public void setup(){
        authService = mock(AuthService.class);
        routeRepository = mock(RouteRepository.class);
        accountService = mock(AccountService.class); // Mock the accountService
        routeService = new RouteService(routeRepository, accountService, authService);
        supplierAccount = Account.builder()
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
        secondRoute = Route.builder()
                .id(3L)
                .departure("Mölndal")
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
    public void Should_ReturnRoute_When_FindRoute_ById(){
        //Arrange
        long routeId = 3L;

        //Mock
        given(routeRepository.findById(routeId)).willReturn(Optional.of(firstRoute));

        //Act
        Optional<Route> foundRoute = routeService.getRouteById(routeId);
        //Assert
        assertThat(foundRoute).isNotNull();
        assertThat(foundRoute.get().getId()).isEqualTo(routeId);

        //verify
        verify(routeRepository, times(1)).findById(routeId);

    }
    @Test
    public void ShouldT_ThrowException_WhenPassingInvalidId(){
        // Arrange
        long routeId = 5L;

        // Act
        assertThrows(ResourceNotFoundException.class,
                () -> routeService.getRouteById(routeId));
    }
    @Test
    void getAllRoutes() {
        // Arrange
        List<Route> listOfRoutes = new ArrayList<>();
        listOfRoutes.add(firstRoute);
        listOfRoutes.add(secondRoute);

        //Mock
        when(routeRepository.findAll()).thenReturn(listOfRoutes);

        //Act

        List<Route> allRoutes = routeService.getAllRoutes();

        //Assert
        assertNotNull(allRoutes);
        assertEquals(listOfRoutes.size(), allRoutes.size());

    }

   /* @Test
    void updateRoute() {
        // Mock
        when(authService.validSupplier(supplierAccount.getId())).thenReturn("You are a supplier");
        when(routeRepository.save(any(Route.class))).thenReturn(firstRoute);

        // Act
        Route result = routeService.updateRouteAsSupplier(firstRoute.getId(), supplierAccount.getId(), secondRoute);

        // Assert
        // Verify
        verify(authService).validSupplier(supplierAccount.getId());
        verify(routeRepository).findById(firstRoute.getId());
        verify(routeRepository).save(firstRoute);

        // Assert
        assertEquals(firstRoute, result);
    } */

    @Test
    void validSupplierToUpdateRoute_Success() {

        when(accountService.getAccountById(anyLong())).thenReturn(Optional.of(supplierAccount));

        String result = routeService.validSupplierToUpdateRoute(supplierAccount.getId(), firstRoute);

        assertEquals("You are allowed to change this route", result);
    }
    @Test
    void validSupplierToUpdateRoute_Failure() {

        when(accountService.getAccountById(anyLong())).thenReturn(Optional.of(supplierAccount));


        firstRoute.setSupplier("DifferentSupplier");


        assertThrows(InvalidInputException.class, () -> {
            routeService.validSupplierToUpdateRoute(supplierAccount.getId(), firstRoute);
        });
    }
    @Test
    public void Should_DeleteAccount_WhenPassingValidId() {
        // Arrange
        long routeId = firstRoute.getId();

        // Mock
        given(routeRepository.findById(routeId)).willReturn(Optional.of(firstRoute));

        // Act
        routeService.deleteOrderById(routeId);

        // Verify
        verify(routeRepository, times(1)).save(firstRoute);
        assert (firstRoute.getAccount() == null);

    }
}