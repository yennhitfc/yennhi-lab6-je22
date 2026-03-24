package com.example.controller.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.controller.Models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}