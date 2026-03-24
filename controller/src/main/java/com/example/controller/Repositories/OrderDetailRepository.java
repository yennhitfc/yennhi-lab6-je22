package com.example.controller.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.controller.Models.Book;
//import com.example.controller.Models.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<com.example.controller.Models.OrderDetail, Long> {
}