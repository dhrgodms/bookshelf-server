package bookshelf.renewal.service;

import bookshelf.renewal.domain.*;
import bookshelf.renewal.dto.ShelfBookDto;
import bookshelf.renewal.dto.request.ShelfBookHaveDto;
import bookshelf.renewal.dto.request.ShelfBookRequestDto;
import bookshelf.renewal.repository.ShelfBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShelfBookService {

    private final ShelfBookRepository shelfBookRepository;
    private final ShelfService shelfService;
    private final BookService bookService;
    private final MemberService memberService;
    private final MemberBookService memberBookService;


    public Page<ShelfBookDto> getAll(Pageable pageable){
        return shelfBookRepository.findAllShelfBooks(pageable);
    }

    public Page<ShelfBookDto> getShelfBookByShelf(Shelf shelf, Pageable pageable) {
        return shelfBookRepository.findAllByShelf(shelf,pageable);
    }

    public Page<ShelfBookDto> getShelfBookByBook(Book book, Pageable pageable) {
        return shelfBookRepository.findAllByBook(book, pageable);
    }

    public ShelfBookDto getShelfBookById(Long id) {
        return shelfBookRepository.findShelfBookById(id);
    }

    public ShelfBookDto saveShelfBook(ShelfBookRequestDto shelfBookRequestDto){
        Shelf findShelf = shelfService.getShelfById(shelfBookRequestDto.getShelfDto().getId());
        Book findBook = bookService.getFindBook(shelfBookRequestDto.getBookDto());
        ShelfBook shelfBook = new ShelfBook(findBook, findShelf);
        shelfBookRepository.save(shelfBook);
        return new ShelfBookDto(shelfBook);
    }

    public Long deleteShelfBook(Long id) {
        Optional<ShelfBook> findShelfBook = shelfBookRepository.findById(id);
        shelfBookRepository.delete(findShelfBook.get());
        return id;
    }

    public ShelfBookDto saveShelfBookById(ShelfBookHaveDto shelfBookHaveDto) {
        Member member = memberService.getMemberByUsername("userA");
        Shelf findShelf = shelfService.getShelfById(shelfBookHaveDto.getShelfId());
        Book findBook = bookService.getFindBook(shelfBookHaveDto.getBookDto());
        memberBookService.saveMemberBook(member, findBook);

        return new ShelfBookDto(shelfBookRepository.save(new ShelfBook(findBook, findShelf)));
    }
}
