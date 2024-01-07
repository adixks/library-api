package pl.szlify.libraryapi.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.szlify.libraryapi.model.BookEntity;
import pl.szlify.libraryapi.model.ClientEntity;

import java.util.List;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    public String buildDailyNotificationEmailContent(List<BookEntity> books) {
        StringBuilder content = new StringBuilder("New books have been added:\n");
        for (BookEntity book : books) {
            content.append("- ").append(book.getTitle()).append(" by ").append(book.getAuthor()).append("\n");
        }
        return content.toString();
    }

    public String buildConfirmationEmailContent(ClientEntity clientEntity, String token) {
        String confirmationUrl = "http://localhost:8080/api/v1/registrationConfirm?token=" + token;
        return "Thank you for your registration, " +
                clientEntity.getFirstName() + " " + clientEntity.getLastName() +
                "! Click the link below to confirm your email address:\n\n" + confirmationUrl;
    }
}
