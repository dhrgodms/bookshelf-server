package bookshelf.renewal.service;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberBook;
import bookshelf.renewal.dto.BookDto;
import bookshelf.renewal.dto.request.BookSaveRequestDto;
import bookshelf.renewal.repository.BookRepository;
import bookshelf.renewal.repository.MemberBookRepository;
import bookshelf.renewal.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberBookServiceTest {

    @InjectMocks
    private MemberBookService memberBookService;

    @Mock
    private BookService bookService;

    @Mock
    private MemberService memberService;

    @Mock
    private MemberBookRepository memberBookRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BookRepository bookRepository;

    @Test
    void haveMemberBook_값이_없으면_createNewMemberBook() {
        //given
        BookDto bookDto = new BookDto("testMemberBook1");
        String username = "userA";
        BookSaveRequestDto dto = new BookSaveRequestDto(bookDto, username);
        Book book = new Book(bookDto.getTitle());
        Member member = new Member(username);
        MemberBook memberBook = new MemberBook(member, book);

        //when에서 나올 코드들의 객체 값을 다 넣어줄 코드를 미리 정의하는 역할
        when(memberService.getMemberByUsername(username)).thenReturn(member);
        when(bookService.getFindBook(dto.getBookDto())).thenReturn(book);
        when(memberBookRepository.findByMemberAndBook(member, book)).thenReturn(Optional.empty());
        when(memberBookRepository.save(any(MemberBook.class))).thenReturn(memberBook);


        //when
        String result = memberBookService.haveMemberBook(dto);

        //then
        assertEquals("[책 저장] testMemberBook1 of userA", result);
    }
}