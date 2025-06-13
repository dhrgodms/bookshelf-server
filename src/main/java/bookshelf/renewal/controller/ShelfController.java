package bookshelf.renewal.controller;

import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberShelf;
import bookshelf.renewal.domain.Shelf;
import bookshelf.renewal.exception.ShelfNotExistException;
import bookshelf.renewal.repository.MemberRepository;
import bookshelf.renewal.repository.MemberShelfRepository;
import bookshelf.renewal.repository.ShelfRepository;
import lombok.Data;
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

    private final ShelfRepository shelfRepository;
    private final MemberRepository memberRepository;
    private final MemberShelfRepository memberShelfRepository;


    //생성
    //전체 조회
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ShelfCreateDto shelfCreateDto){
        Member findMember = memberRepository.findByUsername(shelfCreateDto.getUsername());
        Shelf shelf = new Shelf(shelfCreateDto.getShelfDto().getShelfName(), findMember);

        Shelf saveShelf = shelfRepository.save(shelf);

        MemberShelf memberShelf = new MemberShelf(findMember, shelf);
        memberShelfRepository.save(memberShelf);

        return ResponseEntity.ok("[책장 저장]"+ saveShelf.getShelfName());
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<?> getShelves(@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Shelf> shelves = shelfRepository.findAll(pageable);
        return ResponseEntity.ok(shelves.map(ShelfResponseDto::new));
    }

    //단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getShelf(@PathVariable("id") Long id) {
        Shelf shelf = shelfRepository.findById(id).orElseThrow(()-> new ShelfNotExistException(id));
        return ResponseEntity.ok(new ShelfResponseDto(shelf));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ShelfUpdateDto shelfUpdateDto) {
        Shelf findShelf = shelfRepository.findById(id).orElseThrow(() -> new ShelfNotExistException(id));
        //주인이라면 버튼 활성화(front)
        //주인인지 확인(back)
        String username = shelfUpdateDto.getUsername();
        if (findShelf.getCreater().getUsername().equals(username)){
            //정상로직
            findShelf.setShelfName(shelfUpdateDto.getChangedShelfName());
            findShelf.setShelfMemo(shelfUpdateDto.getChangedShelfMemo());
        } else{
            throw new IllegalArgumentException("[ERROR][PUT]/shelves/" + id + "  Permission Denied");
        }

        return ResponseEntity.ok("[책장 수정]" + findShelf.getShelfName() + " 변경 사항 저장");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Shelf shelf = shelfRepository.findById(id).orElseThrow(() -> new ShelfNotExistException(id));
        // TODO 만약 username이 주인이면 삭제하도록 수정
        shelfRepository.delete(shelf);
        return ResponseEntity.ok("[책장 삭제]" + id);
    }

    @Data
    static class ShelfCreateDto {
        private String username;
        private ShelfDto shelfDto;
    }

    @Data
    static class ShelfDto {
        private String shelfName;
        private String shelfMemo;
    }

    @Data
    static class ShelfResponseDto {
        private Long id;
        private String shelfName;
        private String shelfMemo;

        public ShelfResponseDto(Shelf shelf) {
            this.id = shelf.getId();
            this.shelfName = shelf.getShelfName();
            this.shelfMemo = shelf.getShelfMemo();
        }
    }

    @Data
    static class ShelfUpdateDto {
        private String username;
        private Long id;
        private String changedShelfName;
        private String changedShelfMemo;
    }
}
