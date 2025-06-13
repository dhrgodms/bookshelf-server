package bookshelf.renewal.repository;

import bookshelf.renewal.domain.ShelfBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfRepository extends JpaRepository<ShelfBook, Long> {
}
