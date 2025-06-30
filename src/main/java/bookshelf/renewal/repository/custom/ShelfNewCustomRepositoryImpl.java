package bookshelf.renewal.repository.custom;

import bookshelf.renewal.domain.Bookshelf;
import bookshelf.renewal.domain.QBookshelf;
import bookshelf.renewal.domain.QShelfNew;
import bookshelf.renewal.domain.ShelfNew;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShelfNewCustomRepositoryImpl implements ShelfNewCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ShelfNew> findAllByBookshelfId(Long id, Pageable pageable) {
        QShelfNew sn = QShelfNew.shelfNew;
        QBookshelf bs = QBookshelf.bookshelf;

        List<ShelfNew> results = jpaQueryFactory.selectFrom(sn)
                .join(sn.bookshelf, bs).fetchJoin()
                .where(sn.bookshelf.id.eq(id))
                .fetch();


        Long total = jpaQueryFactory.select(sn.count())
                .from(sn)
                .join(sn.bookshelf, bs)
                .where(sn.bookshelf.id.eq(id))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}
