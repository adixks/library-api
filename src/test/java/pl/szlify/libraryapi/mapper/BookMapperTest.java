package pl.szlify.libraryapi.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szlify.libraryapi.model.BookEntity;
import pl.szlify.libraryapi.model.dto.BookCreateDto;
import pl.szlify.libraryapi.model.dto.BookDto;

@ExtendWith(MockitoExtension.class)
public class BookMapperTest {

    @InjectMocks
    private BookMapper bookMapper;

    @Mock
    private BookCreateDto bookCreateDto;

    private final Faker faker = new Faker();

    @Test
    public void testToEntity() {
        // Given
        String title = faker.book().title();
        String author = faker.book().author();
        String category = faker.book().genre();

        when(bookCreateDto.getTitle()).thenReturn(title);
        when(bookCreateDto.getAuthor()).thenReturn(author);
        when(bookCreateDto.getCategory()).thenReturn(category);

        // When
        BookEntity bookEntity = bookMapper.toEntity(bookCreateDto);

        // Then
        assertEquals(title, bookEntity.getTitle());
        assertEquals(author, bookEntity.getAuthor());
        assertEquals(category, bookEntity.getCategory());
    }

    @Test
    public void testToDto() {
        // Given
        Long id = faker.number().randomNumber();
        String title = faker.book().title();
        String author = faker.book().author();
        String category = faker.book().genre();

        BookEntity bookEntity = new BookEntity()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setCategory(category);

        // When
        BookDto bookDto = bookMapper.toDto(bookEntity);

        // Then
        assertEquals(id, bookDto.getId());
        assertEquals(title, bookDto.getTitle());
        assertEquals(author, bookDto.getAuthor());
        assertEquals(category, bookDto.getCategory());
    }
}
