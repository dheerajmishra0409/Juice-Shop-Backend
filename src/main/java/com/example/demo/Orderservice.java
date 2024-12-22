package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Orderservice {
    @Autowired
    private Orderrepo orderRepository;

    public Orders saveOrder(Orders order) {
        return orderRepository.save(order);
    }
    
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
}
 