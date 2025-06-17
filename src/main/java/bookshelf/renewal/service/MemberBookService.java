package bookshelf.renewal.service;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberBook;
import bookshelf.renewal.dto.BookSaveRequestDto;
import bookshelf.renewal.exception.MemberBookNotExistException;
import bookshelf.renewal.repository.MemberBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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
        Book findBook = bookService.getFindBook(dto);
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
        Book findBook = bookService.getFindBook(dto);
        Member findMember = memberService.getMemberByUsername(dto.getUsername());

        MemberBook memberBook = getMemberBookByMemberAndBook(findMember, findBook);

        if (memberBook.reverseThumb()) {
            log.info("[책 좋아요] {} of {}", memberBook.getBookTitle(), memberBook.getMemberUsername());
        } else {
            log.info("[책 좋아요][취소] {} of {}", memberBook.getBookTitle(), memberBook.getMemberUsername());
        }

        return "[책 좋아요] " + memberBook.getBookTitle() + " of " + memberBook.getMemberUsername();
    }

    public MemberBook getMemberBook(Member member, Book book) {
        return memberBookRepository.save(new MemberBook(member, book));
    }

    public MemberBook getMemberBookByMemberAndBook(Member member, Book book) {
        return memberBookRepository.findByMemberAndBook(member, book).orElseGet(() -> getMemberBook(member, book));
    }
}
