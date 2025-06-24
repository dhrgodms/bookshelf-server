package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.QMember;
import bookshelf.renewal.domain.QMemberShelf;
import bookshelf.renewal.domain.QShelf;
import bookshelf.renewal.dto.MemberShelfDto;
import bookshelf.renewal.dto.QMemberDto;
import bookshelf.renewal.dto.QMemberShelfDto;
import bookshelf.renewal.dto.QShelfDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static bookshelf.renewal.domain.QMemberShelf.memberShelf;

@Repository
@RequiredArgsConstructor
public class MemberShelfCustomRepositoryImpl implements MemberShelfCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<MemberShelfDto> findAllByMember(String username, Pageable pageable) {
        QMemberShelf ms = memberShelf;
        QMember m = QMember.member;
        QShelf s = QShelf.shelf;

        List<MemberShelfDto> results = jpaQueryFactory.select(
                        new QMemberShelfDto(
                                ms.id
                                , new QMemberDto(m.username)
                                , new QShelfDto(s.shelfName, s.shelfMemo, new QMemberDto(s.creator.username)))
                ).from(ms)
                .join(ms.member, m)
                .join(ms.shelf, s)
                .fetch();

        Long total = jpaQueryFactory
                .select(ms.count())
                .from(ms)
                .join(ms.member, m)
                .join(ms.shelf, s)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<MemberShelfDto> findAllOwnShelves(String username, Pageable pageable) {
        QMemberShelf ms = memberShelf;
        QMember m = QMember.member;
        QShelf s = QShelf.shelf;

        List<MemberShelfDto> results = jpaQueryFactory.select(
                        new QMemberShelfDto(
                                ms.id
                                , new QMemberDto(m.username)
                                , new QShelfDto(s.shelfName, s.shelfMemo, new QMemberDto(s.creator.username)))
                ).from(ms)
                .where(ms.member.username.eq(username),
                        ms.shelf.creator.username.eq(username))
                .join(ms.member, m)
                .join(ms.shelf, s)
                .fetch();

        Long total = jpaQueryFactory
                .select(ms.count())
                .from(ms)
                .where(ms.member.username.eq(username),
                        ms.shelf.creator.username.eq(username))
                .join(ms.member, m)
                .join(ms.shelf, s)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<MemberShelfDto> findAllNotCreator(String username,Pageable pageable) {
        QMemberShelf ms = memberShelf;
        QMember m = QMember.member;
        QShelf s = QShelf.shelf;

        List<MemberShelfDto> results = jpaQueryFactory.select(
                        new QMemberShelfDto(
                                ms.id
                                , new QMemberDto(m.username)
                                , new QShelfDto(s.shelfName, s.shelfMemo, new QMemberDto(s.creator.username)))
                ).from(ms)
                .where(ms.member.username.eq(username),
                        ms.shelf.creator.username.ne(username))
                .join(ms.member, m)
                .join(ms.shelf, s)
                .fetch();

        Long total = jpaQueryFactory
                .select(ms.count())
                .from(ms)
                .where(ms.member.username.eq(username),
                        ms.shelf.creator.username.ne(username))
                .join(ms.member, m)
                .join(ms.shelf, s)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}

