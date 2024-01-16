package pl.szlify.libraryapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szlify.libraryapi.exceptions.ClientNotEnabledException;
import pl.szlify.libraryapi.exceptions.LackOfClientException;
import pl.szlify.libraryapi.exceptions.LackOfSubscriptionException;
import pl.szlify.libraryapi.mapper.SubscriptionMapper;
import pl.szlify.libraryapi.model.ClientEntity;
import pl.szlify.libraryapi.model.SubscriptionEntity;
import pl.szlify.libraryapi.model.dto.SubscriptionCreateDto;
import pl.szlify.libraryapi.model.dto.SubscriptionDto;
import pl.szlify.libraryapi.repository.ClientRepository;
import pl.szlify.libraryapi.repository.SubscriptionRepository;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    public void testAddSubscription() {
        // Given
        SubscriptionCreateDto subscriptionCreateDto = new SubscriptionCreateDto();
        subscriptionCreateDto.setClientId(1L);
        subscriptionCreateDto.setCategory("category");
        subscriptionCreateDto.setAuthor("author");

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setEnabled(true);

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setClientEntity(clientEntity);
        subscriptionEntity.setCategory("category");
        subscriptionEntity.setAuthor("author");

        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setClientId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));
        when(subscriptionMapper.toEntity(any(SubscriptionCreateDto.class), any(ClientEntity.class))).thenReturn(subscriptionEntity);
        when(subscriptionRepository.save(any(SubscriptionEntity.class))).thenReturn(subscriptionEntity);
        when(subscriptionMapper.toDto(any(SubscriptionEntity.class))).thenReturn(subscriptionDto);

        // When
        SubscriptionDto result = subscriptionService.addSubscription(subscriptionCreateDto);

        // Then
        assertEquals(subscriptionDto, result);
    }

    @Test
    public void testAddSubscription_ClientNotEnabledException() {
        // Given
        SubscriptionCreateDto subscriptionCreateDto = new SubscriptionCreateDto();
        subscriptionCreateDto.setClientId(1L);
        subscriptionCreateDto.setCategory("category");
        subscriptionCreateDto.setAuthor("author");

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setEnabled(false);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));

        // When - Then
        assertThrows(ClientNotEnabledException.class,
                () -> subscriptionService.addSubscription(subscriptionCreateDto),
                "Expected ClientNotEnabledException");
    }

    @Test
    public void testAddSubscription_LackOfClientException() {
        // Given
        SubscriptionCreateDto subscriptionCreateDto = new SubscriptionCreateDto();
        subscriptionCreateDto.setClientId(1L);
        subscriptionCreateDto.setCategory("category");
        subscriptionCreateDto.setAuthor("author");

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        // When - Then
        assertThrows(LackOfClientException.class,
                () -> subscriptionService.addSubscription(subscriptionCreateDto),
                "Expected LackOfClientException");
    }

    @Test
    public void testDeleteSubscription() {
        // Given
        when(subscriptionRepository.existsById(1L)).thenReturn(true);

        // When
        subscriptionService.deleteSubscription(1L);

        // Then
        verify(subscriptionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteSubscription_LackOfSubscriptionException() {
        // Given
        when(subscriptionRepository.existsById(1L)).thenReturn(false);

        // When - Then
        assertThrows(LackOfSubscriptionException.class,
                () -> subscriptionService.deleteSubscription(1L),
                "Expected LackOfSubscriptionException");
    }
}
