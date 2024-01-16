package pl.szlify.libraryapi.mapper;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szlify.libraryapi.model.ClientEntity;
import pl.szlify.libraryapi.model.SubscriptionEntity;
import pl.szlify.libraryapi.model.dto.SubscriptionCreateDto;
import pl.szlify.libraryapi.model.dto.SubscriptionDto;

@ExtendWith(MockitoExtension.class)
public class SubscriptionMapperTest {

    @InjectMocks
    private SubscriptionMapper subscriptionMapper;

    @Mock
    private SubscriptionEntity subscriptionEntity;

    @Mock
    private ClientEntity clientEntity;

    private final Faker faker = new Faker();

    @Test
    public void testToDto() {
        // Given
        Long id = faker.number().randomNumber();
        Long clientId = faker.number().randomNumber();
        String category = faker.book().genre();
        String author = faker.book().author();

        when(clientEntity.getId()).thenReturn(clientId);
        when(subscriptionEntity.getId()).thenReturn(id);
        when(subscriptionEntity.getClientEntity()).thenReturn(clientEntity);
        when(subscriptionEntity.getCategory()).thenReturn(category);
        when(subscriptionEntity.getAuthor()).thenReturn(author);

        // When
        SubscriptionDto subscriptionDto = subscriptionMapper.toDto(subscriptionEntity);

        // Then
        assertNotNull(subscriptionDto);
        assertEquals(id, subscriptionDto.getId());
        assertEquals(clientId, subscriptionDto.getClientId());
        assertEquals(category, subscriptionDto.getCategory());
        assertEquals(author, subscriptionDto.getAuthor());
    }

    @Test
    public void testToEntity() {
        // Given
        String category = faker.book().genre();
        String author = faker.book().author();

        SubscriptionCreateDto subscriptionCreateDto = new SubscriptionCreateDto()
                .setCategory(category)
                .setAuthor(author);

        // When
        SubscriptionEntity subscriptionEntity = subscriptionMapper.toEntity(subscriptionCreateDto, clientEntity);

        // Then
        assertEquals(clientEntity, subscriptionEntity.getClientEntity());
        assertEquals(category, subscriptionEntity.getCategory());
        assertEquals(author, subscriptionEntity.getAuthor());
    }
}
