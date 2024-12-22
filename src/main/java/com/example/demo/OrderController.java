package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
    @Autowired
    private Orderservice orderService;

    @PostMapping
    public ResponseEntity<Orders> saveOrder(@RequestBody Orders order) {
        double totalCost = order.getItems().stream()
                .mapToDouble(item -> item.getCost() * item.getQuantity())
                .sum();
        order.setTotalCost(totalCost);

        Orders savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(savedOrder);
    }
    
    @GetMapping
    public List<Orders> getOrders() {
        return orderService.getAllOrders();
    }
}
