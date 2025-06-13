package bookshelf.renewal.controller;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberBook;
import bookshelf.renewal.dto.BookDto;
import bookshelf.renewal.exception.BookNotExistException;
import bookshelf.renewal.repository.BookRepository;
import bookshelf.renewal.repository.MemberBookRepository;
import bookshelf.renewal.repository.MemberRepository;
import jakarta.websocket.server.PathParam;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** 책 자체 엔티티 관련 컨트롤러
 *  회원과 상관없이 db에 저장된 책 엔티티를 다룸
 */
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final MemberBookRepository memberBookRepository;

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody BookSaveRequestDto dto){
        //검색
        Book findBook = bookRepository.findByIsbn(dto.getBookDto().getIsbn())
                .orElseGet(() -> {
                    BookDto bookDto = dto.getBookDto();
                    Book book = new Book(bookDto.getTitle(), bookDto.getAuthor(), bookDto.getPublisher(), bookDto.getIsbn(), bookDto.getSeriesName(), bookDto.getCover(), bookDto.getLink(), bookDto.getCategoryName(), bookDto.getPubdate());
            return bookRepository.save(book);
        });

        //저장
        Member findMember = memberRepository.findByUsername(dto.getUsername());
        memberBookRepository.save(new MemberBook(findMember, findBook));
        return ResponseEntity.ok("[책 저장] " + findBook.getTitle());
    }

    @GetMapping("/")
    public ResponseEntity<Page<BookDto>> getAll(@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Book> books = bookRepository.findAll(pageable);
        return ResponseEntity.ok(books.map(b -> new BookDto(b)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> get(@PathParam("id") Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotExistException(id));
        return ResponseEntity.ok(new BookDto(book));
    }



    @Data
    static class BookSaveRequestDto {
        private String username;
        private BookDto bookDto;
    }
}
