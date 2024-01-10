package pl.szlify.libraryapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.szlify.libraryapi.model.BookEntity;
import pl.szlify.libraryapi.model.ClientEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        // Given
        String to = "test@example.com";
        String subject = "Test Subject";
        String content = "Test Content";

        // When
        emailService.sendEmail(to, subject, content);

        // Then
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testBuildDailyNotificationEmailContent() {
        // Given
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setTitle("Test Title");
        book.setAuthor("Test Author");
        books.add(book);

        // When
        String result = emailService.buildDailyNotificationEmailContent(books);

        // Then
        assertTrue(result.contains("Test Title"));
        assertTrue(result.contains("Test Author"));
    }

    @Test
    public void testBuildConfirmationEmailContent() {
        // Given
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstName("Test");
        clientEntity.setLastName("User");
        String token = "testToken";

        // When
        String result = emailService.buildConfirmationEmailContent(clientEntity, token);

        // Then
        assertTrue(result.contains("Test User"));
        assertTrue(result.contains(token));
    }
}
