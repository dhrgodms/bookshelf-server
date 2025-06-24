package bookshelf.renewal.service;

import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberShelf;
import bookshelf.renewal.domain.Shelf;
import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.dto.request.ShelfCreateDto;
import bookshelf.renewal.dto.ShelfDto;
import bookshelf.renewal.dto.request.ShelfUpdateDto;
import bookshelf.renewal.exception.ShelfNotExistException;
import bookshelf.renewal.repository.MemberShelfRepository;
import bookshelf.renewal.repository.ShelfRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShelfService {
    private final ShelfRepository shelfRepository;
    private final MemberShelfRepository memberShelfRepository;
    private final MemberService memberService;

    private Shelf getNewShelf(ShelfCreateDto shelfCreateDto, Member member){
        return shelfRepository.save(new Shelf(shelfCreateDto.getShelfDto().getShelfName(), member));
    }

    public String save(ShelfCreateDto shelfCreateDto){
        Member findMember = memberService.getMemberByUsername(shelfCreateDto.getUsername());
        Shelf shelf = getNewShelf(shelfCreateDto, findMember);
        MemberShelf memberShelf = new MemberShelf(findMember, shelf);
        memberShelfRepository.save(memberShelf);
        log.info("[책장 생성]: {} of  {}", shelf.getShelfName(), findMember.getUsername());

        return "[책장 생성]: " + shelf.getShelfName() + " of "+  findMember.getUsername();
    }

    public Page<ShelfDto> getShelfAll(Pageable pageable) {
        Page<Shelf> shelves = shelfRepository.findAll(pageable);
        return shelves.map(ShelfDto::new);
    }

    public Shelf getShelfById(Long id) {
        return shelfRepository.findById(id).orElseThrow(()-> new ShelfNotExistException(id));
    }

    public String deleteShelf(Long id, MemberDto memberDto){
        Shelf shelf = shelfRepository.findById(id).orElseThrow(() -> new ShelfNotExistException(id));

        Member findMember = memberService.getMemberByUsername(memberDto.getUsername());
        if(shelf.getCreator().equals(findMember)){
            shelfRepository.delete(shelf);
            log.info("[책장 삭제] {} of {}", shelf.getShelfName(), findMember.getUsername());
            return "[책장 삭제] " + shelf.getShelfName() + " of " + findMember.getUsername();

        }else{
            return "[ERROR][DELETE]/shelves/" + id + "  Permission Denied";
        }
    }

    @Transactional
    public String updateShelf(Long id, ShelfUpdateDto shelfUpdateDto) {
        Shelf findShelf = shelfRepository.findById(id).orElseThrow(() -> new ShelfNotExistException(id));

        //주인이라면 버튼 활성화(front)
        //주인인지 확인(back)
        String username = shelfUpdateDto.getUsername();
        if (findShelf.getCreator().getUsername().equals(username)){
            //정상로직
            findShelf.setShelfName(shelfUpdateDto.getChangedShelfName());
            findShelf.setShelfMemo(shelfUpdateDto.getChangedShelfMemo());
        } else{
            throw new IllegalArgumentException("[ERROR][PUT]/shelves/" + id + "  Permission Denied");
        }

        log.info("[책장 수정] {} 변경 사항 저장", findShelf.getShelfName() );
        return "[책장 수정]" + findShelf.getShelfName() + " 변경 사항 저장";
    }

    public ShelfDto findShelfWithBooks(Long id, Pageable pageable) {
        return shelfRepository.findShelfWithBooks(id, pageable);
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

}
