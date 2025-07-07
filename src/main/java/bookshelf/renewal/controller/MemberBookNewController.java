package bookshelf.renewal.controller;

import bookshelf.renewal.dto.MemberBookDto;
import bookshelf.renewal.dto.MemberBookNewDto;
import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.dto.request.BookKeepRequestDto;
import bookshelf.renewal.service.MemberBookNewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memberbooksnew")
public class MemberBookNewController {

    private final MemberBookNewService memberBookNewService;

    @GetMapping
    public ResponseEntity<Page<MemberBookNewDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(memberBookNewService.getAllMemberBooks(pageable));
    }

    //소장 전체 조회(Member에 따른 소장 도서 전체 조회)
    @PostMapping("/member")
    public ResponseEntity<Page<MemberBookNewDto>> getAllByMember(Pageable pageable) {
        return ResponseEntity.ok(memberBookNewService.getMemberBookDtosByMember(pageable));
    }

    //단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberBookNewDto> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberBookNewService.getMemberBookById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberBookNewService.deleteMemberBookById(id));
    }

    @PostMapping("/have")
    public ResponseEntity<Page<MemberBookDto>> getAllHave(@RequestBody MemberDto memberDto, Pageable pageable) {
//        return ResponseEntity.ok(memberBookNewService.getMemberBooksByMemberAndHave(memberDto, pageable));
        return null;
    }

    // 책 저장하기
    @PostMapping
    public ResponseEntity<String> have(@RequestBody BookKeepRequestDto dto){
        return ResponseEntity.ok(memberBookNewService.ownMemberBook(dto));
    }

    //like 조회 TODO thumb 전용 테이블로 이전
    @GetMapping("/thumb")
    public ResponseEntity<Page<MemberBookDto>> getAllThumb(@RequestBody MemberDto memberDto, Pageable pageable) {
//        return ResponseEntity.ok(memberBookNewService.getMemberBooksByMemberAndThumb(memberDto, pageable));
        return null;
    }

    // 책 좋아요 누르기
    @PostMapping("/like")
    public ResponseEntity<String> like(@RequestBody BookKeepRequestDto dto){
//        return ResponseEntity.ok(memberBookNewService.likeMemberBook(dto));
        return null;
    }
}
