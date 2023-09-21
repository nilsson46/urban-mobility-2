package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private boolean isPaymentConfirmed;

    @Column(name = "payment_history", nullable = false)
    private int paymentHistory;

    @Column(name = "active_orders", nullable = false)
    private int activeOrders;

    public Account setUsername(String username) {
        this.username = username;
        return this; // Return the modified object
    }

}
