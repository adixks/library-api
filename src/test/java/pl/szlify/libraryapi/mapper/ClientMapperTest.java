package pl.szlify.libraryapi.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szlify.libraryapi.model.ClientEntity;
import pl.szlify.libraryapi.model.dto.ClientCreateDto;
import pl.szlify.libraryapi.model.dto.ClientDto;

@ExtendWith(MockitoExtension.class)
public class ClientMapperTest {

    @InjectMocks
    private ClientMapper clientMapper;

    @Mock
    private ClientCreateDto clientCreateDto;

    private final Faker faker = new Faker();

    @Test
    public void testToEntity() {
        // Given
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();

        when(clientCreateDto.getFirstName()).thenReturn(firstName);
        when(clientCreateDto.getLastName()).thenReturn(lastName);
        when(clientCreateDto.getEmail()).thenReturn(email);

        // When
        ClientEntity clientEntity = clientMapper.toEntity(clientCreateDto);

        // Then
        assertEquals(firstName, clientEntity.getFirstName());
        assertEquals(lastName, clientEntity.getLastName());
        assertEquals(email, clientEntity.getEmail());
    }

    @Test
    public void testToDto() {
        // Given
        Long id = faker.number().randomNumber();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();

        ClientEntity clientEntity = new ClientEntity()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email);

        // When
        ClientDto clientDto = clientMapper.toDto(clientEntity);

        // Then
        assertEquals(id, clientDto.getId());
        assertEquals(firstName, clientDto.getFirstName());
        assertEquals(lastName, clientDto.getLastName());
        assertEquals(email, clientDto.getEmail());
    }
}
