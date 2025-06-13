package bookshelf.renewal.controller;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberBook;
import bookshelf.renewal.dto.BookDto;
import bookshelf.renewal.repository.BookRepository;
import bookshelf.renewal.repository.MemberBookRepository;
import bookshelf.renewal.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final MemberBookRepository memberBookRepository;

    @PostMapping("/save")
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

    @Data
    static class BookSaveRequestDto {
        private String username;
        private BookDto bookDto;
    }
}
