package com.example.controller.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.controller.Models.Book;
import com.example.controller.Repositories.BookRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) {

        bookRepository.save(new Book(null,"Java","/images/book1.jpg",120000.0,"A","IT"));
bookRepository.save(new Book(null,"Spring","/images/book2.jpg",150000.0,"B","IT"));
bookRepository.save(new Book(null,"Python","/images/book3.jpg",130000.0,"C","IT"));
bookRepository.save(new Book(null,"Clean Code","/images/book4.jpg",180000.0,"D","IT"));
bookRepository.save(new Book(null,"Đắc Nhân Tâm","/images/book5.jpg",90000.0,"E","Business"));
bookRepository.save(new Book(null,"Nhà Giả Kim","/images/book6.jpg",80000.0,"F","Novel"));
}
}