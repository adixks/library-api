package pl.szlify.libraryapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szlify.libraryapi.mapper.BookMapper;
import pl.szlify.libraryapi.model.ClientEntity;
import pl.szlify.libraryapi.model.dto.BookCreateDto;
import pl.szlify.libraryapi.model.dto.BookDto;
import pl.szlify.libraryapi.model.BookEntity;
import pl.szlify.libraryapi.model.SubscriptionEntity;
import pl.szlify.libraryapi.repository.BookRepository;
import pl.szlify.libraryapi.repository.SubscriptionRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testAddBook() {
        // Given
        BookCreateDto bookCreateDto = new BookCreateDto();
        BookEntity bookEntity = new BookEntity();
        BookDto bookDto = new BookDto();

        when(bookMapper.toEntity(bookCreateDto)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(bookMapper.toDto(bookEntity)).thenReturn(bookDto);

        // When
        BookDto result = bookService.addBook(bookCreateDto);

        // Then
        assertEquals(bookDto, result);
        verify(bookRepository, times(1)).save(bookEntity);
    }

    @Test
    public void testSendDailyNotifications() {
        // Given
        BookEntity bookEntity = new BookEntity();
        List<BookEntity> booksAddedToday = Arrays.asList(bookEntity);
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setEmail("test@example.com");
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setClientEntity(clientEntity);
        List<SubscriptionEntity> subscriptions = Arrays.asList(subscriptionEntity);
        String to = "test@example.com";
        String subject = "New books added";
        String content = "Content";

        when(bookRepository.findBooksAddedToday()).thenReturn(booksAddedToday);
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);
        when(emailService.buildDailyNotificationEmailContent(booksAddedToday)).thenReturn(content);

        // When
        bookService.sendDailyNotifications();

        // Then
        verify(emailService, times(subscriptions.size())).sendEmail(to, subject, content);
    }
}
