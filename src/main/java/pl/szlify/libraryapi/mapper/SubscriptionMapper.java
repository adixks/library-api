package pl.szlify.libraryapi.mapper;

import org.springframework.stereotype.Component;
import pl.szlify.libraryapi.model.ClientEntity;
import pl.szlify.libraryapi.model.SubscriptionEntity;
import pl.szlify.libraryapi.model.dto.SubscriptionCreateDto;
import pl.szlify.libraryapi.model.dto.SubscriptionDto;

@Component
public class SubscriptionMapper {
    public SubscriptionDto toDto(SubscriptionEntity subscriptionEntity) {
        return new SubscriptionDto()
                .setId(subscriptionEntity.getId())
                .setClientId(subscriptionEntity.getClientEntity().getId())
                .setCategory(subscriptionEntity.getCategory())
                .setAuthor(subscriptionEntity.getAuthor());
    }

    public SubscriptionEntity toEntity(SubscriptionCreateDto subscriptionCreateDto, ClientEntity clientEntity) {
        return new SubscriptionEntity()
                .setClientEntity(clientEntity)
                .setCategory(subscriptionCreateDto.getCategory())
                .setAuthor(subscriptionCreateDto.getAuthor());
    }
}
