package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "bank_account_number", nullable = false)
    private String bankAccountNumber;

    @Column(name = "is_payment_confirmed", nullable = false)
    private boolean paymentConfirmed;

    @Column(name = "payment_history", nullable = false)
    private int paymentHistory;

   /* @OneToMany(targetEntity = Order.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    //@Column(name = "active_orders", nullable = false)
    private List<Order> activeOrders; */
   @OneToMany(targetEntity = Route.class,cascade = CascadeType.ALL)
   @JoinColumn(name = "account_id", referencedColumnName = "account_id")
   //@Column(name = "active_orders", nullable = false)
   private List<Route> routes;

    public Account setUsername(String username) {
        this.username = username;
        return this; // Return the modified object
    }

    public void setRoutes(List<Route> route) {
        this.routes = route;
    }

}
