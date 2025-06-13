package bookshelf.renewal.repository;

import bookshelf.renewal.domain.MemberBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBookRepository extends JpaRepository<MemberBook, Long> {
}
