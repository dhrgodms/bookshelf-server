package bookshelf.renewal.controller;

import bookshelf.renewal.common.auth.CustomUserDetails;
import bookshelf.renewal.common.auth.SecurityUtil;
import bookshelf.renewal.dto.BookshelfDto;
import bookshelf.renewal.dto.request.BookshelfCreateDto;
import bookshelf.renewal.repository.BookshelfRepository;
import bookshelf.renewal.service.BookshelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bookshelves")
@RequiredArgsConstructor
public class BookshelfController {

    private final BookshelfRepository bookshelfRepository;
    private final BookshelfService bookshelfService;

    @GetMapping
    public ResponseEntity<Page<BookshelfDto>> getAll(Pageable pageable){

        return ResponseEntity.ok(bookshelfService.getAll(pageable));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<List<MemberBookNewDto>> getById(@PathVariable("id") Long id){
//        return ResponseEntity.ok(bookshelfService.getById(id));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(bookshelfService.getMapById(id));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody BookshelfCreateDto dto){
        return ResponseEntity.ok(bookshelfService.save(dto));
    }

    @GetMapping("/member")
    public ResponseEntity<List<BookshelfDto>> getAllByMember(Pageable pageable){
        CustomUserDetails currentUserDetails = SecurityUtil.getCurrentUserDetails();
        Long memberId = currentUserDetails.getMemberId();
        List<BookshelfDto> results = bookshelfService.getAllByMemberId(memberId, pageable);

        return ResponseEntity.ok(results);
    }
}
