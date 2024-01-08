package pl.szlify.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szlify.libraryapi.model.SubscriptionEntity;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    List<SubscriptionEntity> findByCategoryOrAuthor(String category, String author);
}
