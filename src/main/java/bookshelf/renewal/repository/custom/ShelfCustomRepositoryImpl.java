package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.QBook;
import bookshelf.renewal.domain.QMember;
import bookshelf.renewal.domain.QShelf;
import bookshelf.renewal.domain.QShelfBook;
import bookshelf.renewal.dto.*;
import bookshelf.renewal.dto.response.QSimpleShelfBookDto;
import bookshelf.renewal.dto.response.SimpleShelfBookDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShelfCustomRepositoryImpl implements ShelfCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ShelfDto findShelfWithBooks(Long shelfId, Pageable pageable) {
        QShelfBook sb= QShelfBook.shelfBook;
        QShelf s = QShelf.shelf;
        QBook b = QBook.book;

        ShelfDto shelfDto = jpaQueryFactory
                .select(new QShelfDto(
                        s.id,
                        s.shelfName,
                        s.shelfMemo,
                        new QMemberDto(s.creator.username)
                ))
                .from(s)
                .where(s.id.eq(shelfId))
                .fetchOne();

        if (shelfDto == null) {
            return null;
        }

        List<BookDto> bookDtos = jpaQueryFactory
                .select(new QBookDto(
                        b.id,
                        b.title,
                        b.author,
                        b.publisher,
                        b.isbn,
                        b.seriesName,
                        b.cover,
                        b.categoryName,
                        b.link,
                        b.pubdate
                ))
                .from(sb)
                .join(sb.book, b)
                .where(sb.shelf.id.eq(shelfId))
                .fetch();

        Long total = jpaQueryFactory
                .select(sb.count())
                .from(sb)
                .where(sb.shelf.id.eq(shelfId))
                .fetchOne();

        PageImpl<BookDto> pagedBooks = new PageImpl<>(bookDtos, pageable, total);
        shelfDto.setBooks(bookDtos);
        shelfDto.setPageInfoDto(new PageInfoDto(pagedBooks.getNumber(), pagedBooks.getSize(), pagedBooks.getTotalElements(), pagedBooks.getTotalPages()));

        return shelfDto;
    }

    @Override
    public ShelfDto findShelfWithShelfBooks(Long shelfId, Pageable pageable) {
        QShelfBook sb= QShelfBook.shelfBook;
        QShelf s = QShelf.shelf;
        QBook b = QBook.book;

        ShelfDto shelfDto = jpaQueryFactory
                .select(new QShelfDto(
                        s.id,
                        s.shelfName,
                        s.shelfMemo,
                        new QMemberDto(s.creator.username)
                ))
                .from(s)
                .where(s.id.eq(shelfId))
                .fetchOne();

        if (shelfDto == null) {
            return null;
        }

        List<SimpleShelfBookDto> results = jpaQueryFactory
                .select(new QSimpleShelfBookDto(
                        sb.id,
                        s.id,
                        new QBookDto(
                                b.id,
                                b.title,
                                b.author,
                                b.publisher,
                                b.isbn,
                                b.seriesName,
                                b.cover,
                                b.categoryName,
                                b.link,
                                b.pubdate
                        ))).from(sb)
                .join(sb.shelf, s)
                .join(sb.book, b)
                .where(sb.shelf.id.eq(shelfId))
                .fetch();

        Long total = jpaQueryFactory
                .select(sb.count())
                .from(sb)
                .where(sb.shelf.id.eq(shelfId))
                .fetchOne();

        PageImpl<SimpleShelfBookDto> pagedBooks = new PageImpl<>(results, pageable, total);
        shelfDto.setShelfBooks(results);
        shelfDto.setPageInfoDto(new PageInfoDto(pagedBooks.getNumber(), pagedBooks.getSize(), pagedBooks.getTotalElements(), pagedBooks.getTotalPages()));

        return shelfDto;
    }

    @Override
    public Page<SimpleShelfBookDto> findShelfBooksWithId(Long shelfId, Pageable pageable) {
        QShelfBook sb= QShelfBook.shelfBook;
        QShelf s = QShelf.shelf;
        QBook b = QBook.book;

        List<SimpleShelfBookDto> results = jpaQueryFactory
                .select(new QSimpleShelfBookDto(
                        sb.id,
                        s.id,
                        new QBookDto(
                                b.id,
                                b.title,
                                b.author,
                                b.publisher,
                                b.isbn,
                                b.seriesName,
                                b.cover,
                                b.categoryName,
                                b.link,
                                b.pubdate
                        ))).from(sb)
                .join(sb.shelf, s)
                .join(sb.book, b)
                .where(sb.shelf.id.eq(shelfId))
                .fetch();


        Long total = jpaQueryFactory.select(sb.count())
                .from(sb)
                .join(sb.shelf, s)
                .join(sb.book, b)
                .where(sb.shelf.id.eq(shelfId))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<ShelfDto> findShelvesByMember(String username, Pageable pageable) {
        QShelf s = QShelf.shelf;
        QMember m = QMember.member;
        QBook b = QBook.book;

        List<ShelfDto> results = jpaQueryFactory
                .select(new QShelfDto(
                        s.id,
                        s.shelfName,
                        s.shelfMemo
                        )).from(s)
                .where(s.creator.username.eq(username))
                .fetch();


        Long total = jpaQueryFactory.select(s.count())
                .from(s)
                .where(s.creator.username.eq(username))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}
