package pl.szlify.libraryapi.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.szlify.libraryapi.model.dto.BookCreateDto;
import pl.szlify.libraryapi.model.dto.BookDto;
import pl.szlify.libraryapi.service.BookService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public BookDto addBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        return bookService.addBook(bookCreateDto);
    }
}
