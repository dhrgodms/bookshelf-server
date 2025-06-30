package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.ShelfNew;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShelfNewCustomRepository {
    Page<ShelfNew> findAllByBookshelfId(Long id, Pageable pageable);
}
