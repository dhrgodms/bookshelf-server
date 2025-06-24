package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.*;
import bookshelf.renewal.dto.MemberBookDto;
import bookshelf.renewal.dto.QBookDto;
import bookshelf.renewal.dto.QMemberBookDto;
import bookshelf.renewal.dto.QMemberDto;
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
public class MemberBookCustomRepositoryImpl implements MemberBookCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<MemberBookDto> findAllMemberBooks(Pageable pageable) {
        QMemberBook mb = QMemberBook.memberBook;
        QMember m = QMember.member;
        QBook b = QBook.book;

        List<MemberBookDto> results = jpaQueryFactory.select(
                        new QMemberBookDto(
                                mb.id,
                                mb.memo,
                                new QBookDto(mb.book.title, mb.book.author, mb.book.publisher, mb.book.isbn, mb.book.seriesName, mb.book.cover, mb.book.categoryName, mb.book.link, mb.book.pubdate),
                                new QMemberDto(m.username),
                                mb.thumb,
                                mb.have
                        )
                ).from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .fetch();

        Long total = jpaQueryFactory.select(mb.count())
                .from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<MemberBookDto> findAllByMember(String username, Pageable pageable) {
        QMemberBook mb = QMemberBook.memberBook;
        QMember m = QMember.member;
        QBook b = QBook.book;

        List<MemberBookDto> results = jpaQueryFactory.select(
                        new QMemberBookDto(
                                mb.id,
                                mb.memo,
                                new QBookDto(mb.book.title, mb.book.author, mb.book.publisher, mb.book.isbn, mb.book.seriesName, mb.book.cover, mb.book.categoryName, mb.book.link, mb.book.pubdate),
                                new QMemberDto(m.username),
                                mb.thumb,
                                mb.have
                        )
                ).from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .where(mb.member.username.eq(username))
                .fetch();

        Long total = jpaQueryFactory.select(mb.count())
                .from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .where(mb.member.username.eq(username))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<MemberBookDto> findAllByBook(Book book, Pageable pageable) {
        QMemberBook mb = QMemberBook.memberBook;
        QMember m = QMember.member;
        QBook b = QBook.book;

        List<MemberBookDto> results = jpaQueryFactory.select(
                        new QMemberBookDto(
                                mb.id,
                                mb.memo,
                                new QBookDto(mb.book.title, mb.book.author, mb.book.publisher, mb.book.isbn, mb.book.seriesName, mb.book.cover, mb.book.categoryName, mb.book.link, mb.book.pubdate),
                                new QMemberDto(m.username),
                                mb.thumb,
                                mb.have
                        )
                ).from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .where(mb.book.eq(book))
                .fetch();

        Long total = jpaQueryFactory.select(mb.count())
                .from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .where(mb.book.eq(book))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<MemberBookDto> findAllByHave(String username, Pageable pageable) {
        QMemberBook mb = QMemberBook.memberBook;
        QMember m = QMember.member;
        QBook b = QBook.book;

        List<MemberBookDto> results = jpaQueryFactory.select(
                        new QMemberBookDto(
                                mb.id,
                                mb.memo,
                                new QBookDto(mb.book.title, mb.book.author, mb.book.publisher, mb.book.isbn, mb.book.seriesName, mb.book.cover, mb.book.categoryName, mb.book.link, mb.book.pubdate),
                                new QMemberDto(m.username),
                                mb.thumb,
                                mb.have
                        )
                ).from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .where(mb.have.eq(true),
                        mb.member.username.eq(username))
                .fetch();

        Long total = jpaQueryFactory.select(mb.count())
                .from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .where(mb.have.eq(true),
                        mb.member.username.eq(username))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<MemberBookDto> findAllByThumb(String username, Pageable pageable) {
        QMemberBook mb = QMemberBook.memberBook;
        QMember m = QMember.member;
        QBook b = QBook.book;

        List<MemberBookDto> results = jpaQueryFactory.select(
                        new QMemberBookDto(
                                mb.id,
                                mb.memo,
                                new QBookDto(mb.book.title, mb.book.author, mb.book.publisher, mb.book.isbn, mb.book.seriesName, mb.book.cover, mb.book.categoryName, mb.book.link, mb.book.pubdate),
                                new QMemberDto(m.username),
                                mb.thumb,
                                mb.have
                        )
                ).from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .where(mb.thumb.eq(true),
                        mb.member.username.eq(username))
                .fetch();

        Long total = jpaQueryFactory.select(mb.count())
                .from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .where(mb.thumb.eq(true),
                        mb.member.username.eq(username))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public MemberBookDto findMemberBookById(Long id) {
        QMemberBook mb = QMemberBook.memberBook;
        QMember m = QMember.member;
        QBook b = QBook.book;

        MemberBookDto result = jpaQueryFactory.select(
                        new QMemberBookDto(
                                mb.id,
                                mb.memo,
                                new QBookDto(mb.book.title, mb.book.author, mb.book.publisher, mb.book.isbn, mb.book.seriesName, mb.book.cover, mb.book.categoryName, mb.book.link, mb.book.pubdate),
                                new QMemberDto(m.username),
                                mb.thumb,
                                mb.have
                        )
                ).from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .where(mb.id.eq(id))
                .fetchOne();

        return result;
    }

    @Override
    public Optional<MemberBook> findMemberBookByMemberAndBook(String username, String isbn) {
        QMemberBook mb = QMemberBook.memberBook;
        QMember m = QMember.member;
        QBook b = QBook.book;

        MemberBook result = jpaQueryFactory.selectFrom(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .where(mb.member.username.eq(username),
                        mb.book.isbn.eq(isbn))
                .fetchOne();

        return Optional.of(result);
    }

    @Override
    public MemberBookDto findByUsernameAndIsbn(String username, String isbn) {
        QMemberBook mb = QMemberBook.memberBook;
        QMember m = QMember.member;
        QBook b = QBook.book;

        MemberBookDto result = jpaQueryFactory.select(
                        new QMemberBookDto(
                                mb.id,
                                mb.memo,
                                new QBookDto(mb.book.title, mb.book.author, mb.book.publisher, mb.book.isbn, mb.book.seriesName, mb.book.cover, mb.book.categoryName, mb.book.link, mb.book.pubdate),
                                new QMemberDto(m.username),
                                mb.thumb,
                                mb.have
                        )
                ).from(mb)
                .join(mb.book, b)
                .join(mb.member, m)
                .where(mb.member.username.eq(username),
                        mb.book.isbn.eq(isbn))
                .fetchOne();

        return result;
    }
}
