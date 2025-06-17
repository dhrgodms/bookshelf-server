package bookshelf.renewal.controller;

import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.service.MemberBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memberbooks")
public class MemberBookController {

    private final MemberBookService memberBookService;

    //전체 조회(Member)
    @GetMapping
    public ResponseEntity<?> getAll(@RequestBody MemberDto memberDto, Pageable pageable) {
        return ResponseEntity.ok(memberBookService.getMemberBooksByMemberAndHave(memberDto, pageable));
    }

    @GetMapping("/like")
    public ResponseEntity<?> getAllLike(@RequestBody MemberDto memberDto, Pageable pageable) {
        return ResponseEntity.ok(memberBookService.getMemberBooksByMemberAndThumb(memberDto, pageable));
    }

    //단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberBookService.getMemberBookById(id));
    }
}
