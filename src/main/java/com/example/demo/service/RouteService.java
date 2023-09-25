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
    public Route updateRoute(long routeId, long accountId, Route route){
        Route fetchedRoute = routeRepository.findById(routeId).get();
        Account account = accountService.getAccountById(accountId).get();
        fetchedRoute.setAccount(account);
        route.setId(routeId);
        return routeRepository.save(route);
    }

    /*public Route updateRouteAsSupplier(long routeId, long accountId, Route route){
        Route fethedRoute = routeRepository.findById(routeId).get();

        fethedRoute.setAccount(account);
        fethedRoute.setId(routeId);
        return  routeRepository.save(route);
    } */
}
