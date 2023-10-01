package com.example.demo.service;

import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private RouteService routeService;
    private AccountService accountService;

    public OrderService(RouteService routeService, AccountService accountService) {
        this.routeService = routeService;
        this.accountService = accountService;
    }

    public void makeAOrder(long routeId, long accountId){
        Account account = accountService.getAccountById(accountId).get();
        if(!account.isPaymentConfirmed()){
            throw new InvalidInputException("You dont have a valid payment");
        }
        routeService.updateRouteById(routeId, accountId);

        int currenPaymentHistory = account.getPaymentHistory();
        account.setPaymentHistory(currenPaymentHistory + 1);
    }

    public void deleteOrder(long routeId) {
        routeService.deleteOrderById(routeId);
    }
}
