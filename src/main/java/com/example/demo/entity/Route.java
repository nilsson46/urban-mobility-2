package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure", nullable = false, unique = true)
    private String departure;
    @Column(name = "arrival", nullable = false, unique = true)
    private String arrival;
    @Column(name = "supplier", nullable = false, unique = true)
    private String supplier;
    @Column(name = "type_of_transport", nullable = false, unique = true)
    private String typeOfTransport;
    @Column(name = "departure_time", nullable = false, unique = true)
    private String departureTime;
    @Column(name = "arrival_time", nullable = false, unique = true)
    private String arrivalTime;
    @Column(name = "price", nullable = false, unique = true)
    private String price;
    @Column(name = "discount", nullable = false, unique = true)
    private String discount;
    @Column(name = "total_price", nullable = false, unique = true)
    private String totalPrice;



}
