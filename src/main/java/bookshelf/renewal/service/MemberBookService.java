package bookshelf.renewal.service;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberBook;
import bookshelf.renewal.dto.MemberBookDto;
import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.dto.request.BookSaveRequestDto;
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
        MemberBook memberBook = getMemberBookByMemberAndBook(dto.getUsername(), dto.getBookDto().getIsbn());

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
        MemberBook memberBook = getMemberBookByMemberAndBook(dto.getUsername(), dto.getBookDto().getIsbn());

        if (memberBook.reverseThumb()) {
            log.info("[책 좋아요] {} of {}", memberBook.getBookTitle(), memberBook.getMemberUsername());
        } else {
            log.info("[책 좋아요][취소] {} of {}", memberBook.getBookTitle(), memberBook.getMemberUsername());
        }

        return "[책 좋아요] " + memberBook.getBookTitle() + " of " + memberBook.getMemberUsername();
    }


    public MemberBook saveMemberBook(Member member, Book book) {
        return memberBookRepository.save(new MemberBook(member, book));
    }

    public MemberBookDto getMemberBookDtoByMemberAndBook(String username, String isbn) {
        return memberBookRepository.findByUsernameAndIsbn(username, isbn);
    }

    public MemberBook getMemberBookByMemberAndBook(String username, String isbn) {
        return memberBookRepository.findMemberBookByMemberAndBook(username, isbn).orElseThrow(() -> new MemberBookNotExistException(username, isbn));
    }

    public Page<MemberBookDto> getMemberBooksByMember(MemberDto memberDto, Pageable pageable) {
        String username = memberDto.getUsername();
        return memberBookRepository.findAllByMember(username, pageable);
    }

    public Page<MemberBookDto> getMemberBooksByMemberAndHave(MemberDto memberDto, Pageable pageable) {
        String username = memberDto.getUsername();
        return memberBookRepository.findAllByHave(username, pageable);
    }

    public Page<MemberBookDto> getMemberBooksByMemberAndThumb(MemberDto memberDto, Pageable pageable) {
        String username = memberDto.getUsername();
        return memberBookRepository.findAllByThumb(username, pageable);
    }

    public MemberBookDto getMemberBookById(Long id) {
        return memberBookRepository.findMemberBookById(id);
    }
}
