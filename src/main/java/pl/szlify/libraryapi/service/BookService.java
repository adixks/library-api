package pl.szlify.libraryapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szlify.libraryapi.mapper.BookMapper;
import pl.szlify.libraryapi.model.BookEntity;
import pl.szlify.libraryapi.model.SubscriptionEntity;
import pl.szlify.libraryapi.model.dto.BookCreateDto;
import pl.szlify.libraryapi.model.dto.BookDto;
import pl.szlify.libraryapi.repository.BookRepository;
import pl.szlify.libraryapi.repository.SubscriptionRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final EmailService emailService;
    private final BookMapper bookMapper;

    public BookDto addBook(BookCreateDto bookCreateDto) {
        BookEntity savedBook = bookRepository.save(bookMapper.toEntity(bookCreateDto));
        return bookMapper.toDto(savedBook);
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyNotifications() {
        List<BookEntity> booksAddedToday = bookRepository.findBooksAddedToday();
        if (!booksAddedToday.isEmpty()) {
            List<SubscriptionEntity> subscriptions = subscriptionRepository.findAll();
            for (SubscriptionEntity subscription : subscriptions) {
                String to = subscription.getClientEntity().getEmail();
                String subject = "New books added";
                String content = emailService.buildDailyNotificationEmailContent(booksAddedToday);
                emailService.sendEmail(to, subject, content);
            }
        }
    }
}
