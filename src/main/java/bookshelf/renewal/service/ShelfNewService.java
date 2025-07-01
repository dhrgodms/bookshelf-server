package bookshelf.renewal.service;

import bookshelf.renewal.domain.Bookshelf;
import bookshelf.renewal.domain.ShelfNew;
import bookshelf.renewal.dto.MemberDto;
import bookshelf.renewal.dto.ShelfNewDto;
import bookshelf.renewal.dto.request.ShelfNewCreateDto;
import bookshelf.renewal.exception.ShelfNotExistException;
import bookshelf.renewal.repository.BookshelfRepository;
import bookshelf.renewal.repository.ShelfNewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShelfNewService {
    private final ShelfNewRepository shelfNewRepository;
    private final BookshelfRepository bookshelfRepository;

    public String save(ShelfNewCreateDto dto) {

        Bookshelf bookshelf = bookshelfRepository.findById(dto.getBookshelfId()).orElseThrow(()->new ShelfNotExistException(dto.getBookshelfId()));
        ShelfNew shelfNew = new ShelfNew(bookshelf, dto.getShelfPosition(), dto.getShelfCustomName());

        log.info("[선반 생성]: {} of  {}", shelfNew.getShelfCustomName(), dto.getUsername());

        shelfNewRepository.save(shelfNew);

        return "[선반 생성]: " + dto.getShelfCustomName() + " of "+  dto.getUsername();
    }

    public ShelfNewDto getShelfById(Long id) {
        return new ShelfNewDto(shelfNewRepository.findById(id).orElseThrow(()->new ShelfNotExistException(id)));
    }

    public String deleteShelf(Long id, MemberDto memberDto) {
        ShelfNew findShelfNew = shelfNewRepository.findById(id).orElseThrow(()->new ShelfNotExistException(id));
        // TODO 회원이 bookshelf 주인이 맞는지 확인하는 로직 추가하기
        shelfNewRepository.delete(findShelfNew);

        log.info("[책장 삭제] {} of {}", findShelfNew.getShelfCustomName(), memberDto.getUsername());
        return "[책장 삭제] " + findShelfNew.getShelfCustomName() + " of " + memberDto.getUsername();
    }

    public Page<ShelfNewDto> getShelfByBookshelf(Long id, Pageable pageable) {
        return shelfNewRepository.findAllByBookshelfId(id, pageable).map(ShelfNewDto::new);
    }
}
