package bookshelf.renewal.controller;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.Shelf;
import bookshelf.renewal.domain.ShelfBook;
import bookshelf.renewal.dto.BookDto;
import bookshelf.renewal.dto.ShelfDto;
import bookshelf.renewal.exception.ShelfBookNotExistException;
import bookshelf.renewal.repository.ShelfBookRepository;
import bookshelf.renewal.service.BookService;
import bookshelf.renewal.service.ShelfService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shelfbooks")
@RequiredArgsConstructor
public class ShelfBookController {
    private final ShelfBookRepository shelfBookRepository;
    private final BookService bookService;
    private final ShelfService shelfService;

    @GetMapping
    public ResponseEntity<Page<ShelfBookDto>> getAll(Pageable pageable){
        return ResponseEntity.ok(shelfBookRepository.findAll(pageable).map(sb->new ShelfBookDto(sb)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShelfBookDto> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ShelfBookDto(shelfBookRepository.findById(id).orElseThrow(() -> new ShelfBookNotExistException(id))));
    }

    //일단 저장(TODO service로 분리 필요)
    @PostMapping
    public ResponseEntity<ShelfBook> add(@RequestBody ShelfBookRequestDto shelfBookRequestDto) {
        Shelf findShelf = shelfService.getShelfById(shelfBookRequestDto.getShelfDto().getId());
        Book findBook = bookService.getFindBook(shelfBookRequestDto.getBookDto());
        ShelfBook shelfBook = new ShelfBook(findBook, findShelf);
        return ResponseEntity.ok(shelfBookRepository.save(shelfBook));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        Optional<ShelfBook> findShelfBook = shelfBookRepository.findById(id);
        shelfBookRepository.delete(findShelfBook.get());
        return ResponseEntity.ok("[책장에서 제외] " + id);
    }


    @Data
    static class ShelfBookDto {
        private Long id;
        private BookDto book;
        private ShelfDto shelf;

        public ShelfBookDto(ShelfBook shelfBook) {
            this.id = shelfBook.getId();
            this.book = new BookDto(shelfBook.getBook());
            this.shelf = new ShelfDto(shelfBook.getShelf());
        }
    }

    @Data
    private class ShelfBookRequestDto {
        private ShelfDto shelfDto;
        private BookDto bookDto;
    }
}
