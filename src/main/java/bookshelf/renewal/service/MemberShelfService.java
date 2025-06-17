package bookshelf.renewal.service;

import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberShelf;
import bookshelf.renewal.domain.Shelf;
import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.dto.MemberShelfDto;
import bookshelf.renewal.exception.MemberShelfNotExistException;
import bookshelf.renewal.repository.MemberShelfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberShelfService {

    private final MemberShelfRepository memberShelfRepository;
    private final ShelfService shelfService;
    private final MemberService memberService;

    public Page<MemberShelfDto> getMemberShelvesByMember(MemberDto memberDto, Pageable pageable) {
        return memberShelfRepository.findAllByMember(memberDto.getUsername(), pageable);
    }

    public MemberShelf getMemberShelfById(Long id) {
        return memberShelfRepository.findMemberShelfById(id).orElseThrow(() -> new MemberShelfNotExistException(id));
    }

    public String deleteMemberShelf(Long id) {
        MemberShelf memberShelf = getMemberShelfById(id);
        memberShelfRepository.delete(memberShelf);
        log.info("[멤버책장 삭제] {} of {}", memberShelf.getShelf().getShelfName(), memberShelf.getMember().getUsername());
        return "[멤버책장 삭제] " + memberShelf.getShelf().getShelfName() + " of " + memberShelf.getMember().getUsername();
    }


    public String subscribeShelf(Long id, MemberDto memberDto) {
        String username = memberDto.getUsername();
        Member findMember = memberService.getMemberByUsername(username);
        Shelf findShelf = shelfService.getShelfById(id);
        MemberShelf memberShelf = new MemberShelf(findMember, findShelf);
        memberShelfRepository.save(memberShelf);

        log.info("[멤버책장 구독] {} -> {} of {}", findMember.getUsername(), findShelf.getShelfName(), findShelf.getCreator().getUsername());
        return "[멤버책장 구독] " + findMember.getUsername() + " -> " + findShelf.getShelfName() + " of " + findShelf.getCreator().getUsername();
    }

    public Page<MemberShelfDto> getMemberShelvesByOwnMember(MemberDto memberDto, Pageable pageable) {
        return memberShelfRepository.findAllOwnShelves(memberDto.getUsername(), pageable);
    }

    public Page<MemberShelfDto> getMemberShelvesByUsername(MemberDto memberDto, Pageable pageable) {
        Page<MemberShelf> all = memberShelfRepository.findAllByMemberUsername(memberDto.getUsername(), pageable);
        return all.map(MemberShelfDto::new);
    }

    public Page<MemberShelfDto> getMemberShelvesBySubscribe(MemberDto memberDto, Pageable pageable) {
        return memberShelfRepository.findAllNotCreator(memberDto.getUsername(), pageable);
    }
}
