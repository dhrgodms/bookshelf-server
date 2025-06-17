package bookshelf.renewal.repository;

import bookshelf.renewal.domain.Book;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
