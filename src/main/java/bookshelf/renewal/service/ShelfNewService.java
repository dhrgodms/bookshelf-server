package bookshelf.renewal.service;

import bookshelf.renewal.common.auth.CustomUserDetails;
import bookshelf.renewal.common.auth.SecurityUtil;
import bookshelf.renewal.domain.Bookshelf;
import bookshelf.renewal.domain.ShelfNew;
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
        CustomUserDetails currentUserDetails = SecurityUtil.getCurrentUserDetails();
        Long memberId = currentUserDetails.getMemberId();

        Bookshelf bookshelf = bookshelfRepository.findById(dto.getBookshelfId()).orElseThrow(()->new ShelfNotExistException(dto.getBookshelfId()));
        ShelfNew shelfNew = new ShelfNew(bookshelf, dto.getShelfPosition(), dto.getShelfCustomName());

        log.info("[선반 생성]: {} of  {}", shelfNew.getShelfCustomName(), memberId);

        shelfNewRepository.save(shelfNew);

        return "[선반 생성]: " + dto.getShelfCustomName() + " of "+  memberId;
    }

    public ShelfNewDto getShelfById(Long id) {
        return new ShelfNewDto(shelfNewRepository.findById(id).orElseThrow(()->new ShelfNotExistException(id)));
    }

    public String deleteShelf(Long id) {
        ShelfNew findShelfNew = shelfNewRepository.findById(id).orElseThrow(()->new ShelfNotExistException(id));

        CustomUserDetails currentUserDetails = SecurityUtil.getCurrentUserDetails();
        Long memberId = currentUserDetails.getMemberId();


        // TODO 회원이 bookshelf 주인이 맞는지 확인하는 로직 추가하기

        shelfNewRepository.delete(findShelfNew);

        log.info("[책장 삭제] {} of {}", findShelfNew.getShelfCustomName(), memberId);
        return "[책장 삭제] " + findShelfNew.getShelfCustomName() + " of " + memberId;
    }

    public Page<ShelfNewDto> getShelfByBookshelf(Long id, Pageable pageable) {
        return shelfNewRepository.findAllByBookshelfId(id, pageable).map(ShelfNewDto::new);
    }
}
