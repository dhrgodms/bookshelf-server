package bookshelf.renewal.repository;

import bookshelf.renewal.domain.ShelfNew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfNewRepository extends JpaRepository<ShelfNew, Long> {
}
