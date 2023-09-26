package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private RouteService routeService;

    public OrderService(RouteService routeService) {
        this.routeService = routeService;
    }

    public void makeAOrder(long routeId, long accountId){
        routeService.updateRouteById(routeId, accountId);
    }

    public void deleteOrder(long routeId) {
        routeService.deleteOrderById(routeId);
    }
}
