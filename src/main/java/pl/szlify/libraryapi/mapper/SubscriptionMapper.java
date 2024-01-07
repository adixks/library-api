package pl.szlify.libraryapi.mapper;

import org.springframework.stereotype.Component;
import pl.szlify.libraryapi.model.SubscriptionEntity;
import pl.szlify.libraryapi.model.dto.SubscriptionDto;

@Component
public class SubscriptionMapper {

    public SubscriptionDto toDto(SubscriptionEntity subscriptionEntity) {
        return new SubscriptionDto()
                .setId(subscriptionEntity.getId())
                .setClientId(subscriptionEntity.getId())
                .setCategory(subscriptionEntity.getCategory())
                .setAuthor(subscriptionEntity.getAuthor());
    }
}
