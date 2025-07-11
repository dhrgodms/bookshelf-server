package bookshelf.renewal.repository;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.repository.custom.BookCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository {

    Optional<Book> findByIsbn(String isbn);

}
