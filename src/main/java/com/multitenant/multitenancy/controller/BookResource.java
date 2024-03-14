package com.multitenant.multitenancy.controller;

import com.multitenant.multitenancy.tenant.book.Book;
import com.multitenant.multitenancy.tenant.book.BookRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookResource {

    private final BookRepository bookRepository;

    public BookResource(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PutMapping("/api/test/book/{title}")
    public Book putBookInLibrary(@PathVariable("title") String title) {
        Book book = Book.builder()
                .title(title)
                .build();
        return bookRepository.save(book);
    }
}
