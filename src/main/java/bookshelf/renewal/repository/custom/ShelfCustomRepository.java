package bookshelf.renewal.repository.custom;

import bookshelf.renewal.dto.ShelfDto;
import org.springframework.data.domain.Pageable;

public interface ShelfCustomRepository {
    ShelfDto findShelfWithBooks(Long shelfId, Pageable pageable);
}
