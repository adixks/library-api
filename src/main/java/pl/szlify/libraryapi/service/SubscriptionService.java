package pl.szlify.libraryapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

@Service
@AllArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final ClientRepository clientRepository;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionDto addSubscription(SubscriptionCreateDto subscriptionCreateDto) {
        ClientEntity clientEntity = clientRepository.findById(subscriptionCreateDto.getClientId())
                .orElseThrow(LackOfClientException::new);
        if (!clientEntity.isEnabled()) {
            throw new ClientNotEnabledException();
        }
        SubscriptionEntity subscriptionEntity = subscriptionMapper.toEntity(subscriptionCreateDto, clientEntity);
        return subscriptionMapper.toDto(subscriptionRepository.save(subscriptionEntity));
    }

    public void deleteSubscription(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new LackOfSubscriptionException();
        }
        subscriptionRepository.deleteById(id);
    }
}
