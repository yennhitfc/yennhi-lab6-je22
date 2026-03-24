package com.example.controller.Models;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String image;
    private Double price;
    private String category; // 🔥 thêm

    public Book() {}

    // 🔥 constructor mới
    public Book(Long id, String title, String image, Double price, String author, String category) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.author = author;
        this.category = category;
    }

    // getter setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}