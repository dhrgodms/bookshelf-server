package bookshelf.renewal.repository;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberBookRepository extends JpaRepository<MemberBook, Long> {
    Optional<MemberBook> findByMemberAndBook(Member member, Book book);

    @Query("select mb from MemberBook mb join fetch mb.book ")
    Page<MemberBook> findAllByMember(Member member, Pageable pageable);

    @Query("select mb from MemberBook mb join fetch mb.book where mb.have=true")
    Page<MemberBook> findAllByMemberAndHave(Member member, Pageable pageable);

    @Query("select mb from MemberBook mb join fetch mb.book where mb.thumb=true")
    Page<MemberBook> findAllByMemberAndThumb(Member member, Pageable pageable);

}

