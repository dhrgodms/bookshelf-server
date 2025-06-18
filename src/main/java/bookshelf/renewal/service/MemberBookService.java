package bookshelf.renewal.service;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberBook;
import bookshelf.renewal.dto.BookSaveRequestDto;
import bookshelf.renewal.dto.MemberBookDto;
import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.exception.MemberBookNotExistException;
import bookshelf.renewal.repository.MemberBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberBookService {
    private final MemberBookRepository memberBookRepository;
    private final BookService bookService;
    private final MemberService memberService;



    @Transactional
    public String haveMemberBook(BookSaveRequestDto dto) {
        //검색
        Book findBook = bookService.getFindBook(dto.getBookDto());
        Member findMember = memberService.getMemberByUsername(dto.getUsername());

        MemberBook memberBook = getMemberBookByMemberAndBook(findMember, findBook);

        if (memberBook.reverseHave()) {
            log.info("[책 저장] {} of {}", memberBook.getBookTitle(), memberBook.getMemberUsername());
        } else {
            log.info("[책 저장][취소] {} of {}", memberBook.getBookTitle(), memberBook.getMemberUsername());
        }

        return "[책 저장] " + memberBook.getBookTitle() + " of " + memberBook.getMemberUsername();
    }

    @Transactional
    public String likeMemberBook(BookSaveRequestDto dto) {
        //검색
        Book findBook = bookService.getFindBook(dto.getBookDto());
        Member findMember = memberService.getMemberByUsername(dto.getUsername());

        MemberBook memberBook = getMemberBookByMemberAndBook(findMember, findBook);

        if (memberBook.reverseThumb()) {
            log.info("[책 좋아요] {} of {}", memberBook.getBookTitle(), memberBook.getMemberUsername());
        } else {
            log.info("[책 좋아요][취소] {} of {}", memberBook.getBookTitle(), memberBook.getMemberUsername());
        }

        return "[책 좋아요] " + memberBook.getBookTitle() + " of " + memberBook.getMemberUsername();
    }


    public MemberBook getNewMemberBook(Member member, Book book) {
        return memberBookRepository.save(new MemberBook(member, book));
    }

    public MemberBook getMemberBookByMemberAndBook(Member member, Book book) {
        MemberBook memberBook = memberBookRepository.findByMemberAndBook(member, book).orElseGet(() -> getNewMemberBook(member, book));
        return memberBook;
    }

    public Page<MemberBookDto> getMemberBooksByMember(MemberDto memberDto, Pageable pageable) {
        String username = memberDto.getUsername();
        Member findMember = memberService.getMemberByUsername(username);
        Page<MemberBook> memberBooks = memberBookRepository.findAllByMember(findMember, pageable);
        return memberBooks.map(MemberBookDto::new);
    }

    public Page<MemberBookDto> getMemberBooksByMemberAndHave(MemberDto memberDto, Pageable pageable) {
        String username = memberDto.getUsername();
        Member findMember = memberService.getMemberByUsername(username);
        Page<MemberBook> memberBooks = memberBookRepository.findAllByMemberAndHave(findMember, pageable);
        return memberBooks.map(MemberBookDto::new);
    }

    public Page<MemberBookDto> getMemberBooksByMemberAndThumb(MemberDto memberDto, Pageable pageable) {
        String username = memberDto.getUsername();
        Member findMember = memberService.getMemberByUsername(username);
        Page<MemberBook> memberBooks = memberBookRepository.findAllByMemberAndThumb(findMember, pageable);
        return memberBooks.map(MemberBookDto::new);
    }

    public MemberBookDto getMemberBookById(Long id) {
        return new MemberBookDto(memberBookRepository.findById(id).orElseThrow(() -> new MemberBookNotExistException(id)));
    }
}
