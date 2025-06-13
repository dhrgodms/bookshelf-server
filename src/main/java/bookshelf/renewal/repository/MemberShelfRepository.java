package bookshelf.renewal.repository;

import bookshelf.renewal.domain.MemberShelf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberShelfRepository extends JpaRepository<MemberShelf, Long> {
}
