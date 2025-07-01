package bookshelf.renewal.repository.custom;

import bookshelf.renewal.dto.ShelfDto;
import bookshelf.renewal.dto.request.ShelfSearchConditionDto;
import bookshelf.renewal.dto.response.SimpleShelfBookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShelfCustomRepository {
    ShelfDto findShelfWithBooks(Long shelfId, Pageable pageable);

    Page<SimpleShelfBookDto> findShelfBooksWithId(Long shelfId, Pageable pageable);
    ShelfDto findShelfWithShelfBooks(Long shelfId, Pageable pageable);

    Page<ShelfDto> findShelvesByMember(String username, Pageable pageable);

    Page<ShelfDto> SearchShelves(ShelfSearchConditionDto conditionDto, Pageable pageable);
    Page<ShelfDto> SearchShelves(String keyword, Pageable pageable);

}
