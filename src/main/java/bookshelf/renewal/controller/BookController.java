package bookshelf.renewal.controller;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberBook;
import bookshelf.renewal.dto.BookDto;
import bookshelf.renewal.dto.BookSaveRequestDto;
import bookshelf.renewal.exception.BookNotExistException;
import bookshelf.renewal.repository.BookRepository;
import bookshelf.renewal.repository.MemberBookRepository;
import bookshelf.renewal.repository.MemberRepository;
import bookshelf.renewal.service.BookService;
import bookshelf.renewal.service.MemberBookService;
import bookshelf.renewal.service.MemberService;
import jakarta.websocket.server.PathParam;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

/** 책 자체 엔티티 관련 컨트롤러
 *  회원과 상관없이 db에 저장된 책 엔티티를 다룸
 */
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final MemberBookService memberBookService;
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody BookSaveRequestDto dto){
        return ResponseEntity.ok(memberBookService.haveMemberBook(dto));
    }

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody BookSaveRequestDto dto){
        return ResponseEntity.ok(memberBookService.likeMemberBook(dto));
    }

    @GetMapping
    public ResponseEntity<Page<BookDto>> getAll(@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Book> books = bookService.getBooks(pageable);
        return ResponseEntity.ok(books.map(b -> new BookDto(b)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> get(@PathParam("id") Long id) {
        Book book = bookService.getBookOrElseThrow(id);
        return ResponseEntity.ok(new BookDto(book));
    }
}
