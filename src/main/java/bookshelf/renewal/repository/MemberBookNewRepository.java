package bookshelf.renewal.repository;

import bookshelf.renewal.domain.*;
import bookshelf.renewal.repository.custom.MemberBookNewCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBookNewRepository extends JpaRepository<MemberBookNew, Long>, MemberBookNewCustomRepository {

    Page<MemberBookNew> findAllMemberBooks(Pageable pageable);

    Page<MemberBookNew> findAllByMember(String username, Pageable pageable);

    boolean existsByMemberAndBookAndBookshelfAndShelfNew(Member member, Book book, Bookshelf bookshelf, ShelfNew shelf);
}

