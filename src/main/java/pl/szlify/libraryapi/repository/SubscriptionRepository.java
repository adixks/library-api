package pl.szlify.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.szlify.libraryapi.model.SubscriptionEntity;

import java.util.stream.Stream;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    @Query("SELECT s FROM SubscriptionEntity s")
    Stream<SubscriptionEntity> streamAll();
}
