package bookshelf.renewal.repository;

import bookshelf.renewal.domain.ShelfBook;
import bookshelf.renewal.repository.custom.ShelfBookCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfBookRepository extends JpaRepository<ShelfBook, Long>, ShelfBookCustomRepository {
}
