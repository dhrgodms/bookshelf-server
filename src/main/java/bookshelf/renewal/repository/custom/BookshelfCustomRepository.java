package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.Bookshelf;
import bookshelf.renewal.domain.MemberBookNew;
import bookshelf.renewal.dto.BookshelfDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookshelfCustomRepository {

    Page<Bookshelf> findAllByUsername(String username, Pageable pageable);
    Page<BookshelfDto> findAllWithCount(Pageable pageable);
    List<MemberBookNew> findBookShelfById(Long id);
    List<BookshelfDto> findBookshelvesByMemberWithCount(String username, Pageable pageable);
}
