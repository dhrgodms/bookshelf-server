package bookshelf.renewal.repository;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberBook;
import bookshelf.renewal.repository.custom.MemberBookCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberBookRepository extends JpaRepository<MemberBook, Long>, MemberBookCustomRepository {

}

