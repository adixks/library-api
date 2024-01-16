package pl.szlify.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szlify.libraryapi.model.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
