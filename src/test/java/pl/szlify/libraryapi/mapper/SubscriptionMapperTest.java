package pl.szlify.libraryapi.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szlify.libraryapi.model.SubscriptionEntity;
import pl.szlify.libraryapi.model.dto.SubscriptionDto;

@ExtendWith(MockitoExtension.class)
public class SubscriptionMapperTest {

    @InjectMocks
    private SubscriptionMapper subscriptionMapper;

    @Mock
    private SubscriptionEntity subscriptionEntity;

    private final Faker faker = new Faker();

    @Test
    public void testToDto() {
        // Given
        Long id = faker.number().randomNumber();
        String category = faker.book().genre();
        String author = faker.book().author();

        when(subscriptionEntity.getId()).thenReturn(id);
        when(subscriptionEntity.getCategory()).thenReturn(category);
        when(subscriptionEntity.getAuthor()).thenReturn(author);

        // When
        SubscriptionDto subscriptionDto = subscriptionMapper.toDto(subscriptionEntity);

        // Then
        assertEquals(id, subscriptionDto.getId());
        assertEquals(category, subscriptionDto.getCategory());
        assertEquals(author, subscriptionDto.getAuthor());
    }
}
