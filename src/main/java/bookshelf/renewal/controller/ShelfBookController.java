package bookshelf.renewal.controller;

import bookshelf.renewal.dto.ShelfBookDto;
import bookshelf.renewal.dto.request.ShelfBookRequestDto;
import bookshelf.renewal.repository.ShelfBookRepository;
import bookshelf.renewal.service.BookService;
import bookshelf.renewal.service.ShelfBookService;
import bookshelf.renewal.service.ShelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shelfbooks")
@RequiredArgsConstructor
public class ShelfBookController {
    private final ShelfBookRepository shelfBookRepository;
    private final ShelfBookService shelfBookService;
    private final BookService bookService;
    private final ShelfService shelfService;

    @GetMapping
    public ResponseEntity<Page<ShelfBookDto>> getAll(Pageable pageable){
        return ResponseEntity.ok(shelfBookService.getAll(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ShelfBookDto> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(shelfBookService.getShelfBookById(id));
    }



    @PostMapping
    public ResponseEntity<ShelfBookDto> add(@RequestBody ShelfBookRequestDto shelfBookRequestDto) {
        return ResponseEntity.ok(shelfBookService.saveShelfBook(shelfBookRequestDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        shelfBookService.deleteShelfBook(id);
        return ResponseEntity.ok("[책장에서 제외] " + id);
    }


}
