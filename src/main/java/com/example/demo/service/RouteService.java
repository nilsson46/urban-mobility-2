package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Route;
import com.example.demo.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private RouteRepository routeRepository;
    private AccountService accountService;

    public RouteService(RouteRepository repository, AccountService accountService) {
        this.routeRepository = repository;
        this.accountService = accountService;
    }

    public Route createRoute(Route route){
        return routeRepository.save(route);
    }

    public List<Route> getAllRoutes(){
        return routeRepository.findAll();
    }

    //Probably orderRoute
    /*public Route updateRouteById(long routeId, long accountId, Route route) {
        Route fetchedTransport = routeRepository.findById(routeId).get();
        Account account = accountService.getAccountById(accountId).get();
        fetchedTransport.setAccount(account);
        fetchedTransport.setId(routeId);
        return routeRepository.save(route);
    } */
    public Route updateRouteById(long routeId, long accountId) {
        Route fetchedTransport = routeRepository.findById(routeId).get();
        Account account = accountService.getAccountById(accountId).get();
        fetchedTransport.setAccount(account);
        fetchedTransport.setId(routeId);
        return routeRepository.save(fetchedTransport);
    }
    //Should be update route as supplier. Need some sort of if to check.
    public Route updateRouteAsSupplier(long routeId, Route route){
        Route fethedRoute = routeRepository.findById(routeId).get();
        fethedRoute.setId(routeId);
        return  routeRepository.save(route);
    }
}
