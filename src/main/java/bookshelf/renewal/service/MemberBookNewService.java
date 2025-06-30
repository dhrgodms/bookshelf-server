package bookshelf.renewal.service;

import bookshelf.renewal.domain.*;
import bookshelf.renewal.dto.MemberBookNewDto;
import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.dto.request.BookKeepRequestDto;
import bookshelf.renewal.exception.MemberBookNotExistException;
import bookshelf.renewal.exception.ShelfBookNotExistException;
import bookshelf.renewal.repository.BookshelfRepository;
import bookshelf.renewal.repository.MemberBookNewRepository;
import bookshelf.renewal.repository.ShelfNewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public String ownMemberBook(BookKeepRequestDto dto) {
        List<Pair<Long, Long>> locationList = dto.getLocation().stream()
                .map(s -> {
                    String[] split = s.split("-");
                    return Pair.of(Long.valueOf(split[0]), Long.valueOf(split[1]));
                })
                .toList();

        Member member = memberService.getMemberByUsername(dto.getUsername());
        Book book = bookService.getFindBook(dto.getBookDto());

        List<String> savedLogs = new ArrayList<>();

        for (Pair<Long, Long> pair : locationList) {
            Long bookshelfId = pair.getLeft();
            Long shelfId = pair.getRight();

            Bookshelf bookshelf = bookshelfRepository.findById(bookshelfId)
                    .orElseThrow(() -> new ShelfBookNotExistException(bookshelfId));
            ShelfNew shelf = shelfNewRepository.findById(shelfId)
                    .orElseThrow(() -> new ShelfBookNotExistException(shelfId));

            // ✅ (username, isbn, bookshelfId, shelfId) 조합으로 중복 검사
            boolean alreadyExists = memberBookNewRepository.existsByMemberAndBookAndBookshelfAndShelfNew(
                    member, book, bookshelf, shelf
            );

            if (alreadyExists) {
                log.info("[중복 위치] 이미 소유한 책 위치: {}, {}", bookshelf.getId(), shelf.getId());
                continue;
            }

            MemberBookNew memberBook = saveMemberBook(member, book, bookshelf, shelf);
            String logMsg = "[책 저장] " + book.getTitle() + " of " + member.getUsername() + " at (" + bookshelfId + "-" + shelfId + ")";
            log.info(logMsg);
            savedLogs.add(logMsg);
        }

        if (savedLogs.isEmpty()) {
            return "[책 저장] 중복된 위치로 인해 새로 저장된 책이 없습니다.";
        }

        return String.join("\n", savedLogs);
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
