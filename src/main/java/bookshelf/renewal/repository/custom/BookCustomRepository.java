package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.Book;

import java.util.List;

public interface BookCustomRepository {
    List<Book> findBookList();

    Book findBook(String title);

    List<String> findBookTitleList(String title);
}
