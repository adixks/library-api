package pl.szlify.libraryapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szlify.libraryapi.exceptions.InvalidTokenException;
import pl.szlify.libraryapi.mapper.ClientMapper;
import pl.szlify.libraryapi.model.ClientEntity;
import pl.szlify.libraryapi.model.VerificationToken;
import pl.szlify.libraryapi.model.dto.ClientCreateDto;
import pl.szlify.libraryapi.model.dto.ClientDto;
import pl.szlify.libraryapi.repository.ClientRepository;
import pl.szlify.libraryapi.repository.VerificationTokenRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final EmailService emailService;
    private final ClientMapper clientMapper;
    private final VerificationTokenRepository tokenRepository;

    public ClientDto addClient(ClientCreateDto clientCreateDto) {
        ClientEntity clientEntity = clientMapper.toEntity(clientCreateDto);
        ClientEntity savedClient = clientRepository.save(clientEntity);
        sendConfirmationEmail(savedClient);
        return clientMapper.toDto(savedClient);
    }

    protected void sendConfirmationEmail(ClientEntity clientEntity) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, clientEntity);
        tokenRepository.save(verificationToken);
        String to = clientEntity.getEmail();
        String subject = "Registration confirmation";
        String content = emailService.buildConfirmationEmailContent(clientEntity, token);
        emailService.sendEmail(to, subject, content);
    }

    public void confirmRegistration(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new InvalidTokenException();
        }

        ClientEntity client = verificationToken.getClient();
        client.setEnabled(true);
        clientRepository.save(client);
    }
}
