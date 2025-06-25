package bookshelf.renewal.controller;

import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.dto.ShelfDto;
import bookshelf.renewal.dto.request.ShelfCreateDto;
import bookshelf.renewal.dto.request.ShelfUpdateDto;
import bookshelf.renewal.service.ShelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * shelf 자체 엔티티 관련 컨트롤러
 * 회원 상관 없이 shelf의 CRUD를 다룬다.
 */
@RequestMapping("/api/v1/shelves")
@RequiredArgsConstructor
@RestController
public class ShelfController {

    private final ShelfService shelfService;

    //생성
    //전체 조회
    @PostMapping
    public ResponseEntity<String> create(@RequestBody ShelfCreateDto shelfCreateDto){
        return ResponseEntity.ok(shelfService.save(shelfCreateDto));
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<Page<ShelfDto>> getShelves(@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.ok(shelfService.getShelfAll(pageable));
    }

    //단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ShelfDto> getShelf(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ShelfDto(shelfService.getShelfById(id)));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody ShelfUpdateDto shelfUpdateDto) {
        return ResponseEntity.ok(shelfService.updateShelf(id, shelfUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id, @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(shelfService.deleteShelf(id, memberDto));
    }


}
