package bookshelf.renewal.repository.custom;

import bookshelf.renewal.dto.ShelfDto;
import bookshelf.renewal.dto.response.SimpleShelfBookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShelfCustomRepository {
    ShelfDto findShelfWithBooks(Long shelfId, Pageable pageable);

    Page<SimpleShelfBookDto> findShelfBooksWithId(Long shelfId, Pageable pageable);
    ShelfDto findShelfWithShelfBooks(Long shelfId, Pageable pageable);
}
