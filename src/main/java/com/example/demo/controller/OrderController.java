package com.example.demo.controller;

import com.example.demo.entity.Route;
import com.example.demo.service.OrderService;
import com.example.demo.service.RouteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/{routeId}/account/{accountId}")
    public String createOrder(
            @PathVariable("routeId") long routeId,
            @PathVariable("accountId") long accountId
           // @RequestBody Route transport
    ) {
        orderService.makeAOrder(routeId, accountId);
        return "Order was successfully made " + " route id of the order: " + routeId;
    }
    @PutMapping("/{accountId}route/{routeId}")
    public String deleteOrder(@PathVariable("accountId") long accountId, @PathVariable("routeId") long routeId){
        orderService.deleteOrder(accountId, routeId);
        return "Order was successfully deleted " + "route id of the order: " + routeId;
    }
}
