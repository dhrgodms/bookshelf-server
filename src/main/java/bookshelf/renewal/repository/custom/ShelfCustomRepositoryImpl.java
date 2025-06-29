package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.QBook;
import bookshelf.renewal.domain.QMember;
import bookshelf.renewal.domain.QShelf;
import bookshelf.renewal.domain.QShelfBook;
import bookshelf.renewal.dto.*;
import bookshelf.renewal.dto.request.ShelfSearchConditionDto;
import bookshelf.renewal.dto.response.QSimpleShelfBookDto;
import bookshelf.renewal.dto.response.SimpleShelfBookDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Override
    public Page<ShelfDto> SearchShelves(String keyword, Pageable pageable) {
        QShelf s = QShelf.shelf;

        BooleanExpression predicate = keyword == null || keyword.isEmpty() ? null : createKeywordPredicate(keyword);

        //쿼리 실행
        List<ShelfDto> results = jpaQueryFactory
                .select(
                        new QShelfDto(s.id, s.shelfName, s.shelfMemo, new QMemberDto(s.creator.username))
                ).from(s)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(createOrderSpecifier(pageable, s))
                .fetch();

        Long total = jpaQueryFactory
                .select(s.count())
                .from(s)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

    @Override
    public Page<ShelfDto> SearchShelves(ShelfSearchConditionDto conditionDto, Pageable pageable) {
        QBook b = QBook.book;
        QShelf s = QShelf.shelf;
        QMember m = QMember.member;

        // 동적 쿼리 조건 생성
        BooleanBuilder builder = new BooleanBuilder();

        // 키워드 검색
        if (StringUtils.hasText(conditionDto.getKeyword())) {
            String[] keywords = conditionDto.getKeyword().trim().split("\\s+");

            for (String word : keywords) {
                builder.or(s.shelfName.containsIgnoreCase(word)
                        .or(s.shelfMemo.containsIgnoreCase(word))
                        .or(s.creator.username.containsIgnoreCase(word)));
            }
        }

        // 이름 필터
        if (StringUtils.hasText(conditionDto.getShelfName())) {
            builder.and(s.shelfName.containsIgnoreCase(conditionDto.getShelfName()));
        }

        // 설명 필터
        if (StringUtils.hasText(conditionDto.getShelfMemo())) {
            builder.and(s.shelfMemo.containsIgnoreCase(conditionDto.getShelfMemo()));
        }

        // 생성 날짜 범위 필터
        if (conditionDto.getFromYear() != null) {
            builder.and(s.createdDate.goe(LocalDate.ofEpochDay(conditionDto.getFromYear()).atStartOfDay()));
        }

        if (conditionDto.getToYear() != null) {
            builder.and(s.createdDate.loe(LocalDate.ofEpochDay(conditionDto.getToYear()).atStartOfDay()));
        }

        if (conditionDto.getCreatorName() != null) {
            builder.and(s.creator.username.containsIgnoreCase(conditionDto.getCreatorName()));
        }

        //쿼리 실행
        List<ShelfDto> results = jpaQueryFactory
                .select(new QShelfDto(s.id, s.shelfName, s.shelfMemo, new QMemberDto(m.username))
                ).from(s)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(createOrderSpecifier(pageable, s))
                .fetch();

        Long total = jpaQueryFactory
                .select(b.count())
                .from(s)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

    private OrderSpecifier<?>[] createOrderSpecifier(Pageable pageable, QShelf s) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        if (pageable.getSort().isEmpty()) {
            //기본정렬 - 최신순
            orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, s.createdDate));
        } else{
            //요청된 정렬 적용
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "shelfName":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, s.shelfName));
                        break;
                    case "shelfMemo":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, s.shelfMemo));
                        break;
                    case "creator":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, s.creator.username));
                        break;
                    default:
                        orderSpecifiers.add(new OrderSpecifier<>(direction, s.createdDate));
                        break;
                }
            }
        }
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    private BooleanExpression createKeywordPredicate(String keyword) {
        //검색어 토큰화 (공백으로 분리)
        String[] keywords = keyword.trim().split("\\s+");

        //초기 조건(항상 false)
        BooleanExpression expression = Expressions.asBoolean(false).isTrue();


        for (String word : keywords) {
            //각 키워드에 OR 조건 생성
            BooleanExpression keywordCondition = QShelf.shelf.shelfName.containsIgnoreCase(word)
                    .or(QShelf.shelf.shelfMemo.containsIgnoreCase(word))
                    .or(QShelf.shelf.creator.username.containsIgnoreCase(word));

            //전체 표현식에 OR로 추가
            expression = expression.or(keywordCondition);

        }
        return expression;
    }
}
