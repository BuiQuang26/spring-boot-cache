package com.example.springcachedemo.service;

import com.example.springcachedemo.domain.Book;
import com.example.springcachedemo.repository.BookRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @CacheEvict(value = "books", allEntries = true)
    @Scheduled(fixedRate = 3000)
    public Book create(Book book) {
        return bookRepository.save(book);
    }

//    @CachePut(value = "books", key = "#id")
    @CacheEvict(value = "books", allEntries = true)
    public Book update(Long id, Book book) {
        var b = bookRepository.findById(id).orElseThrow();
        if(book.getName() != null && !book.getName().equals(""))
            b.setName(book.getName());
        if(book.getAuthor() != null && !book.getAuthor().equals(""))
            b.setAuthor(book.getAuthor());
        return bookRepository.save(b);
    }

    @CacheEvict(value = "books", allEntries = true)
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Cacheable("books")
    public List<Book> findAll() throws InterruptedException {
        Thread.sleep(2000);
        return bookRepository.findAll();
    }

    @Cacheable(value = "books", key = "#id")
    public Book findBook(Long id) throws InterruptedException {
        Thread.sleep(2000);
        return bookRepository.findById(id).orElseThrow();
    }
}
