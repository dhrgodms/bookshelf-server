package bookshelf.renewal.controller;

import bookshelf.renewal.dto.request.BookSaveRequestDto;
import bookshelf.renewal.dto.MemberBookDto;
import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.service.MemberBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memberbooks")
public class MemberBookController {

    private final MemberBookService memberBookService;

    //소장 전체 조회(Member)
    @GetMapping
    public ResponseEntity<Page<MemberBookDto>> getAll(@RequestBody MemberDto memberDto, Pageable pageable) {
        return ResponseEntity.ok(memberBookService.getMemberBooksByMember(memberDto, pageable));
    }

    @PostMapping("/have")
    public ResponseEntity<Page<MemberBookDto>> getAllHave(@RequestBody MemberDto memberDto, Pageable pageable) {
        return ResponseEntity.ok(memberBookService.getMemberBooksByMemberAndHave(memberDto, pageable));
    }

    //like 조회
    @GetMapping("/thumb")
    public ResponseEntity<Page<MemberBookDto>> getAllThumb(@RequestBody MemberDto memberDto, Pageable pageable) {
        return ResponseEntity.ok(memberBookService.getMemberBooksByMemberAndThumb(memberDto, pageable));
    }

    //단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberBookDto> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberBookService.getMemberBookById(id));
    }

    // 책 저장하기
    @PostMapping
    public ResponseEntity<String> have(@RequestBody BookSaveRequestDto dto){
        return ResponseEntity.ok(memberBookService.haveMemberBook(dto));
    }

    // 책 좋아요 누르기
    @PostMapping("/like")
    public ResponseEntity<String> like(@RequestBody BookSaveRequestDto dto){
        return ResponseEntity.ok(memberBookService.likeMemberBook(dto));
    }
}
