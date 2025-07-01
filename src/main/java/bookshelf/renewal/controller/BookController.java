package bookshelf.renewal.controller;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.dto.BookDto;
import bookshelf.renewal.dto.request.BookSaveRequestDto;
import bookshelf.renewal.service.BookService;
import bookshelf.renewal.service.MemberBookService;
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

    private final MemberBookService memberBookService;
    private final BookService bookService;

    // 책 저장하기
    @PostMapping
    public ResponseEntity<BookDto> save(@RequestBody BookSaveRequestDto dto){
        return ResponseEntity.ok(new BookDto(bookService.saveBook(dto)));
    }

    @GetMapping
    public ResponseEntity<Page<BookDto>> getAll(@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.ok(bookService.getBooks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> get(@PathVariable("id") Long id) {
        Book book = bookService.getBookOrElseThrow(id);
        return ResponseEntity.ok(new BookDto(book));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookDto>> searchBookByKeyword(@RequestParam("q") String query, Pageable pageable) {
        return ResponseEntity.ok(bookService.searchBookByKeyword(query, pageable));
    }
}
