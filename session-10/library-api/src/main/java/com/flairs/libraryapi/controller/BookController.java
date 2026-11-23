
package com.flairs.libraryapi.controller;

import com.flairs.libraryapi.dto.BookResponse;
import com.flairs.libraryapi.dto.CreateBookRequest;
import com.flairs.libraryapi.model.Book;
import com.flairs.libraryapi.service.BookService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookResponse> findAll(
            @RequestParam(required = false) String author) {

        return service.findAll(author)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public BookResponse findById(@PathVariable Long id) {
        return toResponse(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(
            @Valid @RequestBody CreateBookRequest request) {

        Book saved = service.create(toBook(request));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();

        return ResponseEntity.created(location)
                .body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public BookResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CreateBookRequest request) {

        return toResponse(service.update(id, toBook(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    private Book toBook(CreateBookRequest request) {
        return new Book(
                null,
                request.title().strip(),
                request.author().strip(),
                request.isbn(),
                request.year(),
                request.copies()
        );
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.id(),
                book.title(),
                book.author(),
                book.isbn(),
                book.year(),
                book.copies()
        );
    }
}
