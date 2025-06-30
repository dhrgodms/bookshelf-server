package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberBookNewCustomRepositoryImpl implements MemberBookNewCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<MemberBookNew> findAllMemberBooks(Pageable pageable) {
        QMemberBookNew mb = QMemberBookNew.memberBookNew;
        QMember m = QMember.member;
        QBook b = QBook.book;
        QBookshelf bs = QBookshelf.bookshelf;
        QShelfNew sn = QShelfNew.shelfNew;

        List<MemberBookNew> results = jpaQueryFactory
                .selectFrom(mb)
                .join(mb.book, b).fetchJoin()
                .join(mb.member, m).fetchJoin()
                .join(mb.bookshelf, bs).fetchJoin()
                .join(mb.shelfNew, sn).fetchJoin()
                .fetch();


        Long total = jpaQueryFactory.select(mb.count())
                .from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .join(mb.bookshelf, bs)
                .join(mb.shelfNew, sn)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<MemberBookNew> findAllByMember(String username, Pageable pageable) {
        QMemberBookNew mb = QMemberBookNew.memberBookNew;
        QMember m = QMember.member;
        QBook b = QBook.book;
        QBookshelf bs = QBookshelf.bookshelf;
        QShelfNew sn = QShelfNew.shelfNew;

        List<MemberBookNew> results = jpaQueryFactory
                .selectFrom(mb)
                .join(mb.book, b).fetchJoin()
                .join(mb.member, m).fetchJoin()
                .join(mb.bookshelf, bs).fetchJoin()
                .join(mb.shelfNew, sn).fetchJoin()
                .where(mb.member.username.eq(username))
                .fetch();


        Long total = jpaQueryFactory.select(mb.count())
                .from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .join(mb.bookshelf, bs)
                .join(mb.shelfNew, sn)
                .where(mb.member.username.eq(username))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<MemberBookNew> findAllByBook(Book book, Pageable pageable) {
        QMemberBookNew mb = QMemberBookNew.memberBookNew;
        QMember m = QMember.member;
        QBook b = QBook.book;
        QBookshelf bs = QBookshelf.bookshelf;
        QShelfNew sn = QShelfNew.shelfNew;

        List<MemberBookNew> memberBookNews = jpaQueryFactory
                .selectFrom(mb)
                .join(mb.book, b).fetchJoin()
                .join(mb.member, m).fetchJoin()
                .join(mb.bookshelf, bs).fetchJoin()
                .join(mb.shelfNew, sn).fetchJoin()
                .where(mb.book.eq(book))
                .fetch();


        Long total = jpaQueryFactory.select(mb.count())
                .from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .join(mb.bookshelf, bs)
                .join(mb.shelfNew, sn)
                .where(mb.book.eq(book))
                .fetchOne();

        return new PageImpl<>(memberBookNews, pageable, total);
    }

    @Override
    public Optional<MemberBookNew> findMemberBookById(Long id) {
        QMemberBookNew mb = QMemberBookNew.memberBookNew;
        QMember m = QMember.member;
        QBook b = QBook.book;
        QBookshelf bs = QBookshelf.bookshelf;
        QShelfNew sn = QShelfNew.shelfNew;

        MemberBookNew result = jpaQueryFactory.selectFrom(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .join(mb.bookshelf, bs)
                .join(mb.shelfNew, sn)
                .where(mb.id.eq(id))
                .fetchOne();

        return Optional.of(result);
    }

    @Override
    public Optional<MemberBookNew> findMemberBookByMemberAndBook(String username, String isbn) {
        QMemberBookNew mb = QMemberBookNew.memberBookNew;
        QMember m = QMember.member;
        QBook b = QBook.book;
        QBookshelf bs = QBookshelf.bookshelf;
        QShelfNew sn = QShelfNew.shelfNew;

        MemberBookNew result = jpaQueryFactory.selectFrom(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .join(mb.bookshelf, bs)
                .join(mb.shelfNew, sn)
                .where(mb.member.username.eq(username),
                        mb.book.isbn.eq(isbn))
                .fetchOne();

        return Optional.of(result);
    }


}
