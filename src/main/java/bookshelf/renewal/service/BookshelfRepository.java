package bookshelf.renewal.service;

import bookshelf.renewal.domain.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookshelfRepository extends JpaRepository<Bookshelf, Long> {
}
