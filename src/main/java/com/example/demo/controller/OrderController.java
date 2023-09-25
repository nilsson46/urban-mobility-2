package com.example.demo.controller;

import com.example.demo.entity.Route;
import com.example.demo.service.RouteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
public class OrderController {

    private RouteService routeService;

    public OrderController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PutMapping("/{routeId}/account/{accountId}")
    public Route createOrder(
            @PathVariable("routeId") long routeId,
            @PathVariable("accountId") long accountId
           // @RequestBody Route transport
    ){
        return routeService.updateRouteById(routeId, accountId);
    }
}
