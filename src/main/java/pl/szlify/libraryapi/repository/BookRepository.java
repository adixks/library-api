package pl.szlify.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.szlify.libraryapi.model.BookEntity;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("SELECT b FROM BookEntity b WHERE b.addedDate = CURRENT_DATE")
    List<BookEntity> findBooksAddedToday();
}
