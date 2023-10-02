package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.Route;
import com.example.demo.service.AccountService;
import com.example.demo.service.RouteService;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/route")
public class RouteController {
    final RouteService routeService;
    final AccountService accountService;

    public RouteController(RouteService routeService, AccountService accountService) {
        this.routeService = routeService;
        this.accountService = accountService;
    }

    @PostMapping("/account/{accountId}")
    public Route createRoute(@PathVariable ("accountId") long accountId, @RequestBody Route route){
        return routeService.createRoute(route,accountId);
    }

    @GetMapping("/{id}")
    public List<Route> getAllRoutes(){
        return routeService.getAllRoutes();
    }

    @PutMapping("/{routeId}/account/{accountId}")
    public Route updateRoute(
            @PathVariable("routeId") long routeId,
            @PathVariable("accountId") long accountId,
            @RequestBody Route route
    ){
        return routeService.updateRouteAsSupplier(routeId, accountId, route);
    }
}
