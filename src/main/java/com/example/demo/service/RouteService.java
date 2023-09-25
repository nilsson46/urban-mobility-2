package com.example.demo.service;

import com.example.demo.entity.Route;
import com.example.demo.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private RouteRepository repository;

    public RouteService(RouteRepository repository) {
        this.repository = repository;
    }

    public Route createRoute(Route route){
        return repository.save(route);
    }

    public List<Route> getAllRoutes(){
        return repository.findAll();
    }
}
