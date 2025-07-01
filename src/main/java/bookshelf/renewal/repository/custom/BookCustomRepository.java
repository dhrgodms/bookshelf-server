package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.dto.BookDto;
import bookshelf.renewal.dto.request.BookSearchConditionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCustomRepository {
    List<Book> findBookList();

    Book findBook(String title);

    List<String> findBookTitleList(String title);

    Page<BookDto> searchBooks(String keyword, Pageable pageable);

    Page<BookDto> searchBooks(BookSearchConditionDto conditionDto, Pageable pageable);
}
