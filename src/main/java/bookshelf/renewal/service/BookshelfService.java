package bookshelf.renewal.service;

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

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookshelfService {

    private final BookshelfRepository bookshelfRepository;
    private final MemberService memberService;

    public String save(BookshelfCreateDto dto) {
        Member findMember = memberService.getMemberByUsername(dto.getUsername());
        Bookshelf bookshelf = new Bookshelf(findMember, dto.getBookshelfName(), dto.getBookshelfColor(), dto.getNotes());

        bookshelfRepository.save(bookshelf);
        log.info("[책장 생성]: {} of  {}", dto.getBookshelfName(), dto.getUsername());

        return "[책장 생성]: " + dto.getBookshelfName() + " of "+  dto.getUsername();
    }

    public List<BookshelfDto> getAllByMember(MemberDto dto, Pageable pageable) {
        return bookshelfRepository.findBookshelvesByMemberWithCount(dto.getUsername(), pageable);
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
}
