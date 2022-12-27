package com.example.springcachedemo.controller;

import com.example.springcachedemo.domain.Book;
import com.example.springcachedemo.repository.BookRepository;
import com.example.springcachedemo.service.BookService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController("/api")
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    public BookController(BookService bookService,
                          BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/books")
    public ResponseEntity<?> create(@RequestBody Book book){
        Book result = bookService.create(book);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book){
        Book result = bookService.update(id, book);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/books")
    public ResponseEntity<?> getBooks() throws InterruptedException {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBooks(@PathVariable Long id) throws InterruptedException {
        var book = bookService.findBook(id);
        return ResponseEntity.ok(book);
    }

}
