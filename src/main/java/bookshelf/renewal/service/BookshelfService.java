package bookshelf.renewal.service;

import bookshelf.renewal.common.auth.CustomUserDetails;
import bookshelf.renewal.common.auth.SecurityUtil;
import bookshelf.renewal.domain.Bookshelf;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberBookNew;
import bookshelf.renewal.dto.*;
import bookshelf.renewal.dto.request.BookshelfCreateDto;
import bookshelf.renewal.repository.BookshelfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookshelfService {

    private final BookshelfRepository bookshelfRepository;
    private final MemberService memberService;

    public String save(BookshelfCreateDto dto) {
        CustomUserDetails currentUserDetails = SecurityUtil.getCurrentUserDetails();
        Long memberId = currentUserDetails.getMemberId();
        Member findMember = memberService.getMemberById(memberId);
        Bookshelf bookshelf = new Bookshelf(findMember, dto.getBookshelfName(), dto.getBookshelfColor(), dto.getNotes());

        bookshelfRepository.save(bookshelf);
        log.info("[책장 생성]: {} of  {}", dto.getBookshelfName(), memberId);

        return "[책장 생성]: " + dto.getBookshelfName() + " of "+  memberId;
    }

    public List<BookshelfDto> getAllByMember(MemberDto dto, Pageable pageable) {
        return bookshelfRepository.findBookshelvesByMemberWithCount(dto.getUsername(), pageable);
    }

    public List<BookshelfDto> getAllByMemberId(Long memberId, Pageable pageable) {
        return bookshelfRepository.findBookshelvesByMemberIdWithCount(memberId, pageable);
    }

    public Page<BookshelfDto> getAll(Pageable pageable) {
        return bookshelfRepository.findAllWithCount(pageable);
    }

    public List<MemberBookNewDto> getById(Long id) {
        List<MemberBookNew> results = bookshelfRepository.findBookShelfById(id);
        return results.stream().map(mb -> new MemberBookNewDto(
                mb.getId(),
                new BookDto(mb.getBook()),
                new MemberDto(mb.getMember()),
                new BookshelfDto(mb.getBookshelf()),
                new ShelfNewDto(mb.getShelfNew()),
                mb.getStatus(),
                mb.getPurchaseDate(),
                mb.getNotes(),
                mb.getCreatedDate(),
                mb.getLastModifiedDate()
        )).collect(Collectors.toList());
    }

    public Map<String, Object> getMapById(Long id) {
        Map<String, Object> results = bookshelfRepository.findBookShelfMapById(id);
        List<MemberBookNew> memberbooks = (List<MemberBookNew>) results.get("memberBooks");

        Map<String, Object> newResults = new HashMap<>();

        List<MemberBookNewDto> memberbooksDto = memberbooks.stream().map(mb -> new MemberBookNewDto(
                mb.getId(),
                new BookDto(mb.getBook()),
                new MemberDto(mb.getMember()),
                new BookshelfDto(mb.getBookshelf()),
                new ShelfNewDto(mb.getShelfNew()),
                mb.getStatus(),
                mb.getPurchaseDate(),
                mb.getNotes(),
                mb.getCreatedDate(),
                mb.getLastModifiedDate()
        )).collect(Collectors.toList());

        Bookshelf bookshelf = (Bookshelf) results.get("bookshelf");
        BookshelfDto bookshelfDto = new BookshelfDto(bookshelf);

        newResults.put("bookshelf", bookshelfDto);
        newResults.put("memberbooks", memberbooksDto);

        return newResults;
    }
}
