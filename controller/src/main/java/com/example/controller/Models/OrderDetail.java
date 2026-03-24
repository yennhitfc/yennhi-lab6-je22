package com.example.controller.Models;

import jakarta.persistence.*;


@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Book book;

    private int quantity;
    private Double price;

    public void setOrder(Order order) { this.order = order; }
    public void setBook(Book book) { this.book = book; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(Double price) { this.price = price; }
}