package pl.szlify.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szlify.libraryapi.model.ClientEntity;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByEmail(String email);
}
