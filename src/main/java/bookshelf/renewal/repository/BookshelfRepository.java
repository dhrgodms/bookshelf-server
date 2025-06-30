package bookshelf.renewal.repository;

import bookshelf.renewal.domain.Bookshelf;
import bookshelf.renewal.dto.record.BookshelfBookCountDto;
import bookshelf.renewal.repository.custom.BookshelfCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookshelfRepository extends JpaRepository<Bookshelf, Long>, BookshelfCustomRepository {

    @Query("select new bookshelf.renewal.dto.record.BookshelfBookCountDto(mb.bookshelf.id, count(mb))" +
            " from MemberBookNew mb" +
            " where mb.bookshelf.id = :bookshelfId"+
            " group by mb.bookshelf.id")
    List<BookshelfBookCountDto> countBooksByBookshelf(@Param("bookshelfId") Long bookshelfId);
}
