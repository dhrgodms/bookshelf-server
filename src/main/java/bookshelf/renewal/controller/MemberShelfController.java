package bookshelf.renewal.controller;

import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.dto.MemberShelfDto;
import bookshelf.renewal.service.MemberShelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/membershelves")
public class MemberShelfController {

    private final MemberShelfService memberShelfService;

    //전체 조회(Member)
//    @GetMapping
//    public ResponseEntity<Page<MemberShelfDto>> getAll(@RequestBody MemberDto memberDto, Pageable pageable) {
//        return ResponseEntity.ok(memberShelfService.getMemberShelvesByMember(memberDto, pageable));
//    }

    @GetMapping
    public ResponseEntity<Page<MemberShelfDto>> getAll(@RequestBody MemberDto memberDto, Pageable pageable) {
        return ResponseEntity.ok(memberShelfService.getMemberShelvesByMember(memberDto, pageable));
    }

    //단건 조회(id)
    @GetMapping("/{id}")
    public ResponseEntity<MemberShelfDto> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new MemberShelfDto(memberShelfService.getMemberShelfById(id)));
    }

    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberShelfService.deleteMemberShelf(id));
    }

    @GetMapping("/subscribe/{id}")
    public ResponseEntity<String> subscribe(@PathVariable("id") Long id, @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(memberShelfService.subscribeShelf(id, memberDto));
    }

    @PostMapping("/own")
    public ResponseEntity<Page<MemberShelfDto>> getAllOwn(@RequestBody MemberDto memberDto, Pageable pageable) {
        return ResponseEntity.ok(memberShelfService.getMemberShelvesByOwnMember(memberDto, pageable));
    }

    @GetMapping("/subscribe")
    public ResponseEntity<Page<MemberShelfDto>> getAllSubscribe(@RequestBody MemberDto memberDto, Pageable pageable) {
        return ResponseEntity.ok(memberShelfService.getMemberShelvesBySubscribe(memberDto, pageable));
    }

}
