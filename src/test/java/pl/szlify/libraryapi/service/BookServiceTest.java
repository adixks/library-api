package pl.szlify.libraryapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szlify.libraryapi.exceptions.BookAdditionTimeExceededException;
import pl.szlify.libraryapi.mapper.BookMapper;
import pl.szlify.libraryapi.model.ClientEntity;
import pl.szlify.libraryapi.model.dto.BookCreateDto;
import pl.szlify.libraryapi.model.dto.BookDto;
import pl.szlify.libraryapi.model.BookEntity;
import pl.szlify.libraryapi.model.SubscriptionEntity;
import pl.szlify.libraryapi.repository.BookRepository;
import pl.szlify.libraryapi.repository.SubscriptionRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private Clock clock;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testSendDailyNotifications() {
        // Given
        BookEntity bookEntity = new BookEntity();
        List<BookEntity> booksAddedToday = Arrays.asList(bookEntity);
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setEmail("test@example.com");
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setClientEntity(clientEntity);
        Stream<SubscriptionEntity> subscriptions = Stream.of(subscriptionEntity);
        String to = "test@example.com";
        String subject = "New books added";
        String content = "Content";

        when(bookRepository.findBooksAddedToday()).thenReturn(booksAddedToday);
        when(subscriptionRepository.streamAll()).thenReturn(subscriptions);
        when(emailService.buildDailyNotificationEmailContent(booksAddedToday)).thenReturn(content);

        // When
        bookService.sendDailyNotifications();

        // Then
        verify(emailService, times(1)).sendEmail(to, subject, content);
    }

    @Test
    public void addBook_SuccessBeforeDeadline() {
        // Given
        BookCreateDto bookCreateDto = new BookCreateDto();
        BookEntity bookEntity = new BookEntity();
        LocalTime beforeDeadline = LocalTime.of(19, 0);
        when(clock.instant()).thenReturn(beforeDeadline.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        when(bookMapper.toEntity(bookCreateDto)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(bookMapper.toDto(bookEntity)).thenReturn(new BookDto());

        // When
        BookDto result = bookService.addBook(bookCreateDto);

        // Then
        assertNotNull(result);
        verify(bookRepository).save(bookEntity);
        verify(bookMapper).toEntity(bookCreateDto);
        verify(bookMapper).toDto(bookEntity);
    }

    @Test
    public void addBook_FailAfterDeadline() {
        // Given
        BookCreateDto bookCreateDto = new BookCreateDto();
        LocalTime afterDeadline = LocalTime.of(21, 0);
        when(clock.instant()).thenReturn(afterDeadline.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        // When - Then
        assertThrows(BookAdditionTimeExceededException.class, () -> bookService.addBook(bookCreateDto));
    }
}
