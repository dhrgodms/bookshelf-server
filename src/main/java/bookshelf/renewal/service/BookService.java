package bookshelf.renewal.service;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.dto.BookDto;
import bookshelf.renewal.dto.request.BookSaveRequestDto;
import bookshelf.renewal.exception.BookNotExistException;
import bookshelf.renewal.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book getFindBook(BookDto dto) {
        Book findBook = bookRepository.findByIsbn(dto.getIsbn())
                .orElseGet(() -> {
                    Book book = new Book(dto.getTitle(), dto.getAuthor(), dto.getPublisher(), dto.getIsbn(), dto.getSeriesName(), dto.getCover(), dto.getLink(), dto.getCategoryName(), dto.getPubdate());
                    return bookRepository.save(book);
                });
        return findBook;
    }


    @NotNull
    public Page<BookDto> getBooks(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(BookDto::new);
    }

    public Book getBookOrElseThrow(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotExistException(id));
    }

    public Book saveBook(BookSaveRequestDto dto) {
        BookDto bookDto = dto.getBookDto();
        Book book = new Book(
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getPublisher(),
                bookDto.getIsbn(),
                bookDto.getSeriesName(),
                bookDto.getCover(),
                bookDto.getLink(),
                bookDto.getCategoryName(),
                bookDto.getPubdate()
        );
        return bookRepository.save(book);
    }

    public Page<BookDto> searchBookByKeyword(String query, Pageable pageable) {
        return bookRepository.searchBooks(query, pageable);
    }
}
