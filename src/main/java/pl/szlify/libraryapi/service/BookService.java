package pl.szlify.libraryapi.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.szlify.libraryapi.exceptions.BookAdditionTimeExceededException;
import pl.szlify.libraryapi.mapper.BookMapper;
import pl.szlify.libraryapi.model.BookEntity;
import pl.szlify.libraryapi.model.SubscriptionEntity;
import pl.szlify.libraryapi.model.dto.BookCreateDto;
import pl.szlify.libraryapi.model.dto.BookDto;
import pl.szlify.libraryapi.repository.BookRepository;
import pl.szlify.libraryapi.repository.SubscriptionRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final EmailService emailService;
    private final BookMapper bookMapper;

    @Value("${cron.expression}")
    private String cronExpression;

    public BookDto addBook(BookCreateDto bookCreateDto) {
        LocalTime now = LocalTime.now();
        LocalTime deadline = LocalTime.of(20, 0);

        if (now.isAfter(deadline)) {
            throw new BookAdditionTimeExceededException(deadline);
        }

        BookEntity savedBook = bookRepository.save(bookMapper.toEntity(bookCreateDto));
        return bookMapper.toDto(savedBook);
    }

    @Scheduled(cron = "${cron.expression}")
    public void sendDailyNotifications() {
        List<BookEntity> booksAddedToday = bookRepository.findBooksAddedToday();
        if (!booksAddedToday.isEmpty()) {
            try (Stream<SubscriptionEntity> subscriptions = subscriptionRepository.streamAll()) {
                subscriptions.forEach(subscription -> {
                    String to = subscription.getClientEntity().getEmail();
                    String subject = "New books added";
                    String content = emailService.buildDailyNotificationEmailContent(booksAddedToday);
                    emailService.sendEmail(to, subject, content);
                });
            }
        }
    }
}
