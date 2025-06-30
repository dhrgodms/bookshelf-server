package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.*;
import bookshelf.renewal.dto.BookshelfDto;
import bookshelf.renewal.dto.record.BookshelfShelfFlatDto;
import bookshelf.renewal.dto.record.ShelfNewSimpleDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookshelfCustomRepositoryImpl implements BookshelfCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final QBookshelf bs = QBookshelf.bookshelf;
    private final QMember m = QMember.member;
    private final QShelfNew sn = QShelfNew.shelfNew;
    private final QMemberBookNew mb = QMemberBookNew.memberBookNew;
    private final QBook b = QBook.book;

    @Override
    public Page<Bookshelf> findAllByUsername(String username, Pageable pageable) {

        List<Bookshelf> results = jpaQueryFactory.selectFrom(bs)
                .join(bs.member, m).fetchJoin()
                .leftJoin(bs.shelves, sn).fetchJoin()
                .where(bs.member.username.eq(username))
                .fetch();


        Long total = jpaQueryFactory.select(bs.count()).from(bs)
                .join(bs.member, m)
                .leftJoin(bs.shelves, sn)
                .where(bs.member.username.eq(username))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }


    @Override
    public Page<BookshelfDto> findAllWithCount(Pageable pageable) {
        List<BookshelfDto> results = jpaQueryFactory
                .select(Projections.constructor(BookshelfDto.class,
                        bs.id,
                        bs.bookshelfName,
                        bs.shelves,
                        bs.bookshelfColor,
                        mb.count(),
                        bs.createdDate,
                        bs.lastModifiedDate
                ))
                .from(bs)
                .join(bs.member, m)
                .leftJoin(mb).on(mb.bookshelf.id.eq(bs.id))
                .fetch();


        Long total = jpaQueryFactory
                .select(bs.count())
                .from(bs)
                .join(bs.member, m)
                .leftJoin(mb).on(mb.bookshelf.id.eq(bs.id))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    /**
     * private Long id;
     * private Book book;
     * private Member member;
     * private Bookshelf bookshelf;
     * private ShelfNew shelfNew;
     * private BookStatus status;
     * private LocalDateTime purchaseDate;
     * private String notes;
     */

    @Override
    public List<MemberBookNew> findBookShelfById(Long id) {
        return  jpaQueryFactory
                .selectFrom(mb)
                .join(mb.book, b).fetchJoin()
                .join(mb.member, m)
                .join(mb.bookshelf, bs).fetchJoin()
                .join(mb.shelfNew, sn).fetchJoin()
                .where(bs.id.eq(id))
                .fetch();
    }

    @Override
    public List<BookshelfDto> findBookshelvesByMemberWithCount(String username, Pageable pageable) {
        List<BookshelfShelfFlatDto> flats = jpaQueryFactory
                .select(Projections.constructor(BookshelfShelfFlatDto.class,
                        bs.id,
                        m.username,
                        bs.bookshelfName,
                        bs.bookshelfColor,
                        JPAExpressions.select(mb.count())
                                .from(mb)
                                .where(mb.bookshelf.id.eq(bs.id)),
                        bs.createdDate,
                        bs.lastModifiedDate,
                        sn.id,
                        sn.shelfCustomName,

                        JPAExpressions
                                .select(b.cover)
                                .from(mb)
                                .join(mb.book, b)
                                .where(mb.bookshelf.id.eq(bs.id))
                                .orderBy(mb.createdDate.desc())
                                .limit(1)
                        ))
                .from(bs)
                .join(bs.member, m)
                .leftJoin(bs.shelves, sn)
                .where(m.username.eq(username))
                .fetch();

        Map<Long, BookshelfDto> bookshelfMap = new LinkedHashMap<>();

        for (BookshelfShelfFlatDto flat : flats) {
            BookshelfDto dto = bookshelfMap.computeIfAbsent(flat.bookshelfId(), id ->
                    new BookshelfDto(
                            flat.bookshelfId(),
                            flat.username(),
                            flat.bookshelfName(),
                            flat.bookshelfColor(),
                            flat.bookshelfBookCount() != null ? flat.bookshelfBookCount() : 0L,
                            flat.createdDate(),
                            flat.lastModifiedDate(),
                            flat.recentBookCover()
                    ));
            if (flat.shelfId() != null) {
                dto.addShelf(new ShelfNewSimpleDto(flat.shelfId(), flat.shelfName()));
            }
        }


        return new ArrayList<>(bookshelfMap.values());
    }
}
