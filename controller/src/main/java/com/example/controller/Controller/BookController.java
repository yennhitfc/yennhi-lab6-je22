package com.example.controller.Controller;

import java.util.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.controller.Models.*;
import com.example.controller.Repositories.OrderDetailRepository;
import com.example.controller.Repositories.OrderRepository;
import com.example.controller.Services.BookService;

import com.example.controller.Models.Order;
import com.example.controller.Models.OrderDetail;



@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // 🔥 LIST + SEARCH + SORT + CATEGORY + PAGINATION
    @GetMapping
    public String listBooks(Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page) {

        int pageSize = 5;

        Sort sorting = Sort.by("id").ascending();

        if ("asc".equals(sort)) sorting = Sort.by("price").ascending();
        if ("desc".equals(sort)) sorting = Sort.by("price").descending();

        PageRequest pageable = PageRequest.of(page, pageSize, sorting);

        Page<Book> bookPage;

        if (keyword != null && !keyword.isEmpty() && category != null && !category.isEmpty()) {
            bookPage = bookService.searchAndFilter(keyword, category, pageable);
        } else if (keyword != null && !keyword.isEmpty()) {
            bookPage = bookService.searchBooks(keyword, pageable);
        } else if (category != null && !category.isEmpty()) {
            bookPage = bookService.filterByCategory(category, pageable);
        } else {
            bookPage = bookService.getAllBooks(pageable);
        }

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("category", category);

        return "books";
    }

    // 🔥 ADD TO CART
    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) cart = new ArrayList<>();

        Book book = bookService.getBookById(id).orElse(null);

        boolean found = false;

        for (CartItem item : cart) {
            if (item.getBook().getId().equals(id)) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }

        if (!found && book != null) {
            cart.add(new CartItem(book, 1));
        }

        session.setAttribute("cart", cart);

        return "redirect:/books";
    }

    // CRUD giữ nguyên
    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        bookService.getBookById(id).ifPresent(book -> model.addAttribute("book", book));
        return "edit-book";
    }

    @PostMapping("/edit")
    public String updateBook(@ModelAttribute Book book) {
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }


    @Autowired
private OrderRepository orderRepository;

@Autowired
private OrderDetailRepository orderDetailRepository;

// ================= CART PAGE =================
@GetMapping("/cart")
public String viewCart(HttpSession session, Model model) {

    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

    double total = 0;

    if (cart != null) {
        for (CartItem item : cart) {
            total += item.getBook().getPrice() * item.getQuantity();
        }
    }

    model.addAttribute("cart", cart);
    model.addAttribute("total", total);

    return "cart";
}

// ================= CHECKOUT =================
@GetMapping("/checkout")
public String checkout(HttpSession session) {

    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

    if (cart == null || cart.isEmpty()) {
        return "redirect:/books";
    }

    Order order = new Order();

    double total = 0;

    for (CartItem item : cart) {

        OrderDetail detail = new OrderDetail();
        detail.setOrder(order);
        detail.setBook(item.getBook());
        detail.setQuantity(item.getQuantity());
        detail.setPrice(item.getBook().getPrice());

        order.getDetails().add(detail);

        total += item.getBook().getPrice() * item.getQuantity();
    }

    order.setTotal(total);

    orderRepository.save(order);

    // clear cart
    session.removeAttribute("cart");

    return "redirect:/books";
}
}