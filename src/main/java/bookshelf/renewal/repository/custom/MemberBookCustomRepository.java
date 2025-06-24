package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.MemberBook;
import bookshelf.renewal.dto.MemberBookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberBookCustomRepository {
    Page<MemberBookDto> findAllMemberBooks(Pageable pageable);
    Page<MemberBookDto> findAllByMember(String username, Pageable pageable);
    Page<MemberBookDto> findAllByBook(Book book, Pageable pageable);
    Page<MemberBookDto> findAllByHave(String username, Pageable pageable);
    Page<MemberBookDto> findAllByThumb(String username, Pageable pageable);
    Optional<MemberBook> findMemberBookByMemberAndBook(String username, String isbn);

    MemberBookDto findMemberBookById(Long id);
    MemberBookDto findByUsernameAndIsbn(String username, String isbn);
}
