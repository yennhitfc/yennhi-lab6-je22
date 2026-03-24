package com.example.controller.Repositories;

import com.example.controller.Models.Book;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    // 🔥 category
    Page<Book> findByCategory(String category, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseAndCategory(String keyword, String category, Pageable pageable);
}