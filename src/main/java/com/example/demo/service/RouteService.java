package com.example.demo.service;

import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.entity.Account;
import com.example.demo.entity.Route;
import com.example.demo.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RouteService {
    private RouteRepository routeRepository;
    private AccountService accountService;

    private AuthService authService;

    public RouteService(RouteRepository routeRepository, AccountService accountService, AuthService authService) {
        this.routeRepository = routeRepository;
        this.accountService = accountService;
        this.authService = authService;
    }

    //Check so role is supplier.
    public Route createRoute(Route route, long accountId){
        authService.validSupplier(accountId);
        //Should just be able to create a route with their own supplier name.
        return routeRepository.save(route);
    }


    public Optional<Route> getRouteById(long routeId){
        Optional<Route> routeOptional = routeRepository.findById(routeId);

        if(routeOptional.isEmpty()){
            throw new ResourceNotFoundException("Route with ID " + routeId + "not found");
        }
        return routeOptional;
    }


    public List<Route> getAllRoutes(){
        return routeRepository.findAll();
    }


    public Route updateRouteById(long routeId, long accountId) {
        Route fetchedTransport = routeRepository.findById(routeId).get();
        Account account = accountService.getAccountById(accountId).get();
        fetchedTransport.setAccount(account);
        fetchedTransport.setId(routeId);
        return routeRepository.save(fetchedTransport);
    }
    public Route deleteOrderById(long routeId) {
        Route fetchedTransport = routeRepository.findById(routeId).get();
        fetchedTransport.setAccount(null);
        fetchedTransport.setId(routeId);
        return routeRepository.save(fetchedTransport);
    }
    //Should be update route as supplier. Need some sort of if to check.
    public Route updateRouteAsSupplier(long routeId, long accountId, Route route){
        authService.validSupplier(accountId);
        Route fethedRoute = routeRepository.findById(routeId).get();
        validSupplierToUpdateRoute(accountId, route);
        fethedRoute.setId(routeId);
        return  routeRepository.save(route);
    }

    public String validSupplierToUpdateRoute(long accountId,Route route){
        Account supplier = accountService.getAccountById(accountId).get();
        if(!supplier.getUsername().equals(route.getSupplier())){
            throw new InvalidInputException("Your are not the same supplier as the route creator");
        }
        return "You are allowed to change this route";
    }
}
