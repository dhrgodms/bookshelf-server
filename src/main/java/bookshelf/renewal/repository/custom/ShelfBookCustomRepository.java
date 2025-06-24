package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.Shelf;
import bookshelf.renewal.dto.ShelfBookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShelfBookCustomRepository {
    Page<ShelfBookDto> findAllShelfBooks(Pageable pageable);
    Page<ShelfBookDto> findAllByShelf(Shelf shelf, Pageable pageable);
    Page<ShelfBookDto> findAllByBook(Book book, Pageable pageable);

    ShelfBookDto findShelfBookById(Long id);
}
