package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure", nullable = false)
    private String departure;
    @Column(name = "arrival", nullable = false)
    private String arrival;
    @Column(name = "supplier", nullable = false)
    private String supplier;
    @Column(name = "type_of_transport", nullable = false)
    private String typeOfTransport;
    @Column(name = "departure_time", nullable = false)
    private String departureTime;
    @Column(name = "arrival_time", nullable = false)
    private String arrivalTime;
    @Column(name = "price", nullable = false)
    private String price;
    @Column(name = "discount", nullable = false)
    private String discount;
    @Column(name = "total_price", nullable = false)
    private String totalPrice;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    //@Column(name = "active_orders", nullable = false)
    private Account account;


}
