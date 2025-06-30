package bookshelf.renewal.controller;

import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.dto.ShelfNewDto;
import bookshelf.renewal.dto.request.ShelfNewCreateDto;
import bookshelf.renewal.service.ShelfNewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * shelf 자체 엔티티 관련 컨트롤러
 * 회원 상관 없이 shelf의 CRUD를 다룬다.
 */
@RequestMapping("/api/v1/shelvesnew")
@RequiredArgsConstructor
@RestController
public class ShelfNewController {

    private final ShelfNewService shelfNewService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ShelfNewCreateDto shelfNewCreateDto){
        return ResponseEntity.ok(shelfNewService.save(shelfNewCreateDto));
    }

    @GetMapping("/bookshelf/{bookshelfId}")
    public ResponseEntity<Page<ShelfNewDto>> getShelves(@PathVariable("bookshelfId") Long bookshelfId, Pageable pageable){
        return ResponseEntity.ok(shelfNewService.getShelfByBookshelf(bookshelfId,pageable));
    }

    //단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ShelfNewDto> getShelf(@PathVariable("id") Long id) {
        return ResponseEntity.ok(shelfNewService.getShelfById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id, @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(shelfNewService.deleteShelf(id, memberDto));
    }

}
