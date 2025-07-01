package bookshelf.renewal.repository;

import bookshelf.renewal.domain.ShelfNew;
import bookshelf.renewal.dto.record.ShelfBookCountDto;
import bookshelf.renewal.repository.custom.ShelfNewCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShelfNewRepository extends JpaRepository<ShelfNew, Long>, ShelfNewCustomRepository {

    @Query("select new bookshelf.renewal.dto.record.ShelfBookCountDto(mb.shelfNew.id, count(mb))" +
            " from MemberBookNew mb" +
            " where mb.shelfNew.id = :shelfNewId" +
            " group by mb.shelfNew.id")
    List<ShelfBookCountDto> countBooksByShelf(@Param("shelfNewId") Long shelfNewId);

}
