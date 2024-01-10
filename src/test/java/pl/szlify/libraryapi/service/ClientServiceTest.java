package pl.szlify.libraryapi.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.szlify.libraryapi.mapper.ClientMapper;
import pl.szlify.libraryapi.model.ClientEntity;
import pl.szlify.libraryapi.model.dto.ClientCreateDto;
import pl.szlify.libraryapi.repository.ClientRepository;
import pl.szlify.libraryapi.repository.VerificationTokenRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private VerificationTokenRepository tokenRepository;

    @InjectMocks
    private ClientService clientService;

    private Faker faker;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        faker = new Faker();
    }

    @Test
    public void testAddClient() {
        // Given
        ClientCreateDto clientCreateDto = new ClientCreateDto();
        clientCreateDto.setEmail(faker.internet().emailAddress());
        clientCreateDto.setFirstName(faker.name().firstName());
        clientCreateDto.setLastName(faker.name().lastName());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setEmail(clientCreateDto.getEmail());
        clientEntity.setFirstName(clientCreateDto.getFirstName());
        clientEntity.setLastName(clientCreateDto.getLastName());

        when(clientMapper.toEntity(any(ClientCreateDto.class))).thenReturn(clientEntity);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(emailService.buildConfirmationEmailContent(any(ClientEntity.class), any(String.class))).thenReturn("Email content");

        // When
        clientService.addClient(clientCreateDto);

        // Then
        verify(clientRepository).save(clientEntity);
        verify(emailService).sendEmail(any(String.class), any(String.class), any(String.class));
    }
}
