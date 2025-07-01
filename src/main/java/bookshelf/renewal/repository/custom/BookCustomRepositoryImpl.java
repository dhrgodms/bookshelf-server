package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.Book;
import bookshelf.renewal.domain.QBook;
import bookshelf.renewal.dto.BookDto;
import bookshelf.renewal.dto.QBookDto;
import bookshelf.renewal.dto.request.BookSearchConditionDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
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

import static bookshelf.renewal.domain.QBook.book;

@Repository
@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Book> findBookList() {
        return jpaQueryFactory
                .selectFrom(book)
                .fetch();
    }

    @Override
    public Book findBook(String title) {
        return jpaQueryFactory.selectFrom(book)
                .where(book.title.eq(title))
                .fetchOne();
    }

    @Override
    public List<String> findBookTitleList(String title) {
        return jpaQueryFactory.select(book.title)
                .from(book)
                .fetch();
    }

    @Override
    public Page<BookDto> searchBooks(String keyword, Pageable pageable) {
        QBook b = book;

        BooleanExpression predicate = keyword == null || keyword.isEmpty() ? null : createKeywordPredicate(keyword);

        //쿼리 실행
        List<BookDto> results = jpaQueryFactory
                .select(new QBookDto(b.id, b.title, b.author, b.publisher, book.isbn, book.seriesName, book.cover, book.categoryName, b.link, b.pubdate)
                ).from(b)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(createOrderSpecifier(pageable, b))
                .fetch();

        Long total = jpaQueryFactory
                .select(b.count())
                .from(b)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

    @Override
    public Page<BookDto> searchBooks(BookSearchConditionDto conditionDto, Pageable pageable) {
        QBook b = QBook.book;

        // 동적 쿼리 조건 생성
        BooleanBuilder builder = new BooleanBuilder();

        // 키워드 검색
        if (StringUtils.hasText(conditionDto.getKeyword())) {
            String[] keywords = conditionDto.getKeyword().trim().split("\\s+");

            for (String word : keywords) {
                builder.or(b.title.containsIgnoreCase(word)
                        .or(b.author.containsIgnoreCase(word))
                        .or(b.publisher.containsIgnoreCase(word))
                        .or(b.isbn.containsIgnoreCase(word)));
            }
        }

        // 저자 필터
        if (StringUtils.hasText(conditionDto.getAuthor())) {
            builder.and(b.author.containsIgnoreCase(conditionDto.getAuthor()));
        }

        // 출판사 필터
        if (StringUtils.hasText(conditionDto.getPublisher())) {
            builder.and(b.publisher.containsIgnoreCase(conditionDto.getPublisher()));
        }

        // 출판년도 범위 필터
        if (conditionDto.getFromYear() != null) {
            builder.and(b.pubdate.goe(LocalDate.ofEpochDay(conditionDto.getFromYear())));
        }

        if (conditionDto.getToYear() != null) {
            builder.and(b.pubdate.loe(LocalDate.ofEpochDay(conditionDto.getToYear())));
        }

        //쿼리 실행
        List<BookDto> results = jpaQueryFactory
                .select(new QBookDto(
                        book.id, book.title, book.author, book.publisher, book.isbn, book.seriesName, book.cover, book.categoryName, book.link, book.pubdate)
                ).from(b)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(createOrderSpecifier(pageable, b))
                .fetch();

        Long total = jpaQueryFactory
                .select(b.count())
                .from(b)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

    private OrderSpecifier<?>[] createOrderSpecifier(Pageable pageable, QBook b) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        if (pageable.getSort().isEmpty()) {
            //기본정렬 - 최신순
            orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, book.createdDate));
        } else{
            //요청된 정렬 적용
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "title":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, b.title));
                        break;
                    case "author":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, b.author));
                        break;
                    case "publisher":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, b.publisher));
                        break;
                    case "isbn":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, b.isbn));
                        break;
                    default:
                        orderSpecifiers.add(new OrderSpecifier<>(direction, b.createdDate));
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
        BooleanExpression expression = null;


        for (String word : keywords) {
            //각 키워드에 OR 조건 생성
            BooleanExpression keywordCondition = QBook.book.title.containsIgnoreCase(word)
                    .or(QBook.book.author.containsIgnoreCase(word))
                    .or(QBook.book.publisher.containsIgnoreCase(word))
                    .or(QBook.book.isbn.containsIgnoreCase(word));

            //전체 표현식에 OR로 추가
//            expression = expression.or(keywordCondition);

            if (expression == null) {
                expression = keywordCondition;
            } else{
                expression = expression.and(keywordCondition);
            }

        }
        return expression;
    }
}


