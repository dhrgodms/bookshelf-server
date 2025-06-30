package bookshelf.renewal.service;

import bookshelf.renewal.domain.*;
import bookshelf.renewal.dto.MemberBookNewDto;
import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.dto.request.BookKeepRequestDto;
import bookshelf.renewal.exception.MemberBookNotExistException;
import bookshelf.renewal.exception.ShelfBookNotExistException;
import bookshelf.renewal.repository.MemberBookNewRepository;
import bookshelf.renewal.repository.ShelfNewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberBookNewService {
    private final MemberBookNewRepository memberBookNewRepository;
    private final BookService bookService;
    private final BookshelfRepository bookshelfRepository;
    private final ShelfNewRepository shelfNewRepository;
    private final MemberService memberService;

    @Transactional
    public String haveMemberBook(BookKeepRequestDto dto) {

        MemberBookNew memberBook = null;
        Bookshelf findBookshelf = bookshelfRepository.findById(dto.getBookshelfId()).orElseThrow(()->new ShelfBookNotExistException(dto.getBookshelfId()));
        ShelfNew findShelf = shelfNewRepository.findById(dto.getShelfId()).orElseThrow(()->new ShelfBookNotExistException(dto.getShelfId()));


        try {
            memberBook = getMemberBookByMemberAndBook(dto.getUsername(), dto.getBookDto().getIsbn());
            memberBook.updateLocation(findBookshelf, findShelf);

        } catch (NullPointerException e){
            Member findMember = memberService.getMemberByUsername(dto.getUsername());
            Book findBook = bookService.getFindBook(dto.getBookDto());
            memberBook = saveMemberBook(findMember, findBook, findBookshelf, findShelf);
        }

        log.info("[책 저장] {} of {}", memberBook.getBook().getTitle(), memberBook.getMember().getUsername());

        return "[책 저장] " + memberBook.getBook().getTitle() + " of " + memberBook.getMember().getUsername();
    }

    @Transactional
    public String likeMemberBook(BookKeepRequestDto dto) {
        //검색
        MemberBookNew memberBook = null;
        Bookshelf findBookshelf = bookshelfRepository.findById(dto.getBookshelfId()).orElseThrow(()->new ShelfBookNotExistException(dto.getBookshelfId()));
        ShelfNew findShelf = shelfNewRepository.findById(dto.getShelfId()).orElseThrow(()->new ShelfBookNotExistException(dto.getShelfId()));

        try {
            memberBook = getMemberBookByMemberAndBook(dto.getUsername(), dto.getBookDto().getIsbn());
            memberBook.updateLocation(findBookshelf, findShelf);

        } catch (NullPointerException e){
            Member findMember = memberService.getMemberByUsername(dto.getUsername());
            Book findBook = bookService.getFindBook(dto.getBookDto());
            memberBook = saveMemberBook(findMember, findBook, findBookshelf, findShelf);
        }

        log.info("[책 저장] {} of {}", memberBook.getBook().getTitle(), memberBook.getMember().getUsername());

        return "[책 저장] " + memberBook.getBook().getTitle() + " of " + memberBook.getMember().getUsername();
    }


    public MemberBookNew saveMemberBook(Member member, Book book, Bookshelf bookShelf, ShelfNew shelf) {
        return memberBookNewRepository.save(new MemberBookNew(member, book, bookShelf, shelf));
    }

    public MemberBookNew getMemberBookByMemberAndBook(String username, String isbn) {
        return memberBookNewRepository.findMemberBookByMemberAndBook(username, isbn).orElseThrow(() -> new MemberBookNotExistException(username, isbn));
    }

    public Page<MemberBookNewDto> getMemberBookDtosByMember(MemberDto memberDto, Pageable pageable) {
        log.info("username={}", memberDto.getUsername());
        Page<MemberBookNew> results = memberBookNewRepository.findAllByMember(memberDto.getUsername(), pageable);
        return results.map(mb -> new MemberBookNewDto(mb));
    }

    public MemberBookNewDto getMemberBookById(Long id) {
        return new MemberBookNewDto(memberBookNewRepository.findById(id).orElseThrow(()->new MemberBookNotExistException(id)));
    }

    public Page<MemberBookNewDto> getAllMemberBooks(Pageable pageable) {
        Page<MemberBookNew> results = memberBookNewRepository.findAllMemberBooks(pageable);
        return results.map(mb -> new MemberBookNewDto(mb));
    }
}
