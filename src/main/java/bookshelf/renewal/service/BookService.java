package bookshelf.renewal.service;

import bookshelf.renewal.controller.BookController;
import bookshelf.renewal.domain.Book;
import bookshelf.renewal.dto.BookDto;
import bookshelf.renewal.dto.BookSaveRequestDto;
import bookshelf.renewal.exception.BookNotExistException;
import bookshelf.renewal.repository.BookRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book getFindBook(BookSaveRequestDto dto) {
        Book findBook = bookRepository.findByIsbn(dto.getBookDto().getIsbn())
                .orElseGet(() -> {
                    BookDto bookDto = dto.getBookDto();
                    Book book = new Book(bookDto.getTitle(), bookDto.getAuthor(), bookDto.getPublisher(), bookDto.getIsbn(), bookDto.getSeriesName(), bookDto.getCover(), bookDto.getLink(), bookDto.getCategoryName(), bookDto.getPubdate());
                    return bookRepository.save(book);
                });
        return findBook;
    }


    @NotNull
    public Page<Book> getBooks(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books;
    }

    public Book getBookOrElseThrow(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotExistException(id));
    }

}
