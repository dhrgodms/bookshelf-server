package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.*;
import bookshelf.renewal.dto.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static bookshelf.renewal.domain.QBook.book;
import static bookshelf.renewal.domain.QShelf.shelf;
import static bookshelf.renewal.domain.QShelfBook.shelfBook;

@Repository
@RequiredArgsConstructor
public class ShelfBookCustomRepositoryImpl implements ShelfBookCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ShelfBookDto> findAllShelfBooks(Pageable pageable) {
        QShelfBook sb = shelfBook;
        QBook b = book;
        QShelf s = shelf;

        List<ShelfBookDto> results = jpaQueryFactory.select(
                        new QShelfBookDto(sb.id,
                                new QBookDto(sb.book.title, sb.book.author, sb.book.publisher, sb.book.isbn, sb.book.seriesName, sb.book.cover, sb.book.categoryName, sb.book.link, sb.book.pubdate),
                                new QShelfDto(s.shelfName, s.shelfMemo, new QMemberDto(s.creator.username)))
                ).from(sb)
                .join(sb.book, b)
                .join(sb.shelf, s)
                .fetch();

        Long total = jpaQueryFactory
                .select(sb.count())
                .from(sb)
                .join(sb.book, b)
                .join(sb.shelf, s)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<ShelfBookDto> findAllByShelf(Shelf shelf, Pageable pageable) {
        QShelfBook sb = shelfBook;
        QBook b = book;
        QShelf s = QShelf.shelf;

        List<ShelfBookDto> results = jpaQueryFactory.select(
                        new QShelfBookDto(sb.id,
                                new QBookDto(sb.book.title, sb.book.author, sb.book.publisher, sb.book.isbn, sb.book.seriesName, sb.book.cover, sb.book.categoryName, sb.book.link, sb.book.pubdate),
                                new QShelfDto(s.shelfName, s.shelfMemo, new QMemberDto(s.creator.username)))
                ).from(sb)
                .join(sb.book, b)
                .join(sb.shelf, s)
                .where(sb.shelf.eq(shelf))
                .fetch();

        Long total = jpaQueryFactory
                .select(sb.count())
                .from(sb)
                .join(sb.book, b)
                .join(sb.shelf, s)
                .where(sb.shelf.eq(shelf))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<ShelfBookDto> findAllByBook(Book book, Pageable pageable) {
        QShelfBook sb = shelfBook;
        QBook b = QBook.book;
        QShelf s = QShelf.shelf;

        List<ShelfBookDto> results = jpaQueryFactory.select(
                        new QShelfBookDto(sb.id,
                                new QBookDto(sb.book.title, sb.book.author, sb.book.publisher, sb.book.isbn, sb.book.seriesName, sb.book.cover, sb.book.categoryName, sb.book.link, sb.book.pubdate),
                                new QShelfDto(s.shelfName, s.shelfMemo, new QMemberDto(s.creator.username)))
                ).from(sb)
                .join(sb.book, b)
                .join(sb.shelf, s)
                .where(sb.book.eq(book))
                .fetch();

        Long total = jpaQueryFactory
                .select(sb.count())
                .from(sb)
                .join(sb.book, b)
                .join(sb.shelf, s)
                .where(sb.book.eq(book))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public ShelfBookDto findShelfBookById(Long id) {
        QShelfBook sb = shelfBook;
        QBook b = QBook.book;
        QShelf s = QShelf.shelf;

        ShelfBookDto result = jpaQueryFactory.select(
                        new QShelfBookDto(sb.id,
                                new QBookDto(sb.book.title, sb.book.author, sb.book.publisher, sb.book.isbn, sb.book.seriesName, sb.book.cover, sb.book.categoryName, sb.book.link, sb.book.pubdate),
                                new QShelfDto(s.shelfName, s.shelfMemo, new QMemberDto(s.creator.username)))
                ).from(sb)
                .join(sb.book, b)
                .join(sb.shelf, s)
                .where(sb.id.eq(id))
                .fetchOne();

        return result;
    }

}
