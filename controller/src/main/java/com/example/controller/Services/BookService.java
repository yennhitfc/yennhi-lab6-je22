package com.example.controller.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.controller.Models.Book;
import com.example.controller.Repositories.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    // 🔥 category
    public Page<Book> filterByCategory(String category, Pageable pageable) {
        return bookRepository.findByCategory(category, pageable);
    }

    public Page<Book> searchAndFilter(String keyword, String category, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCaseAndCategory(keyword, category, pageable);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}