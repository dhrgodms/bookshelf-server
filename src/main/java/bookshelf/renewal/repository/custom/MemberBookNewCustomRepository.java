package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.MemberBookNew;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberBookNewCustomRepository {
    Page<MemberBookNew> findAllMemberBooks(Pageable pageable);

    Page<MemberBookNew> findAllByMember(String username, Pageable pageable);

    Page<MemberBookNew> findAllByBook(Book book, Pageable pageable);

    Optional<MemberBookNew> findMemberBookById(Long id);

    Optional<MemberBookNew> findMemberBookByMemberAndBook(String username, String isbn);
}
