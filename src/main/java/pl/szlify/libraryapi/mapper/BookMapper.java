package pl.szlify.libraryapi.mapper;

import org.springframework.stereotype.Component;
import pl.szlify.libraryapi.model.BookEntity;
import pl.szlify.libraryapi.model.dto.BookCreateDto;
import pl.szlify.libraryapi.model.dto.BookDto;

@Component
public class BookMapper {

    public BookEntity toEntity(BookCreateDto bookCreateDto) {
        return new BookEntity()
                .setTitle(bookCreateDto.getTitle())
                .setAuthor(bookCreateDto.getAuthor())
                .setCategory(bookCreateDto.getCategory());
    }

    public BookDto toDto(BookEntity bookEntity) {
        return new BookDto()
                .setId(bookEntity.getId())
                .setTitle(bookEntity.getTitle())
                .setAuthor(bookEntity.getAuthor())
                .setCategory(bookEntity.getCategory());
    }
}
