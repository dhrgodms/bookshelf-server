package bookshelf.renewal;


import bookshelf.renewal.domain.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * 책 2권
 */
@Component
@RequiredArgsConstructor
public class BookInitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
        initService.memberInit();
        initService.shelfInit();
        initService.memberBookInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1(){
            String title = "환등기 - 잉마르 베리만 자서전";
            String author = "잉마르 베리만 (지은이), 신견식 (옮긴이)";
            String publisher= "민음사";
            String isbn="8937428547";
            String seriesName = "";
            String cover =  "https://image.aladin.co.kr/product/36468/39/cover200/8937428547_1.jpg";
            String categoryName =  "국내도서>예술/대중문화>영화/드라마>영화감독/배우";
            String link =  "https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=364683901&amp;partner=openAPI&amp;start=api";
            LocalDate pubdate = LocalDate.of(2025, 5, 23);

            Book book1 = new Book(title, author, publisher, isbn, seriesName, cover, link, categoryName, pubdate);
            em.persist(book1);
        }

        public void dbInit2(){
            String title = "급류";
            String author = "정대건 (지은이)";
            String publisher= "민음사";
            String isbn="8937473402";
            String seriesName = "오늘의 젊은 작가 40";
            String cover =  "https://image.aladin.co.kr/product/30769/24/cover200/8937473402_1.jpg";
            String categoryName =  "국내도서>소설/시/희곡>한국소설>2000년대 이후 한국소설";
            String link =  "https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=307692409&amp;partner=openAPI&amp;start=api";
            LocalDate pubdate = LocalDate.of(2022, 12, 22);

            Book book2 = new Book(title, author, publisher, isbn, seriesName, cover, link, categoryName, pubdate);
            em.persist(book2);
        }

        public void memberInit(){
            Member userA = new Member("userA");
            em.persist(userA);
            Member userB = new Member("userB");
            em.persist(userB);
        }

        public void shelfInit() {
            Member userA = em.find(Member.class, 1);
            Member userB = em.find(Member.class, 2);
            Bookshelf bookshelf1 = new Bookshelf(userA, "userA의 안방 책장 1번");
            Bookshelf bookshelf2 = new Bookshelf(userA, "userA의 거실 책장 1번");
            Bookshelf bookshelf3 = new Bookshelf(userB, "userB의 안방 책장 1번");
            Bookshelf bookshelf4 = new Bookshelf(userB, "userB의 거실 책장 1번");

            em.persist(bookshelf1);
            em.persist(bookshelf2);
            em.persist(bookshelf3);
            em.persist(bookshelf4);

            ShelfNew shelfNew1 = new ShelfNew(bookshelf1, 1);
            ShelfNew shelfNew2 = new ShelfNew(bookshelf2, 1);
            ShelfNew shelfNew3 = new ShelfNew(bookshelf3, 1);
            ShelfNew shelfNew4 = new ShelfNew(bookshelf4, 1);

            em.persist(shelfNew1);
            em.persist(shelfNew2);
            em.persist(shelfNew3);
            em.persist(shelfNew4);
        }

        public void memberBookInit(){
            Member userA = em.find(Member.class, 1);
            Member userB = em.find(Member.class, 2);
            Book book1 = em.find(Book.class, 1);
            Book book2 = em.find(Book.class, 2);

            MemberBookNew memberBookNew1 = new MemberBookNew(userA, book1);
            MemberBookNew memberBookNew2 = new MemberBookNew(userA, book2);
            MemberBookNew memberBookNew3 = new MemberBookNew(userB, book1);
            MemberBookNew memberBookNew4 = new MemberBookNew(userB, book2);

            em.persist(memberBookNew1);
            em.persist(memberBookNew2);
            em.persist(memberBookNew3);
            em.persist(memberBookNew4);

            Bookshelf bookshelf1 = em.find(Bookshelf.class, 1);
            Bookshelf bookshelf2 = em.find(Bookshelf.class, 2);
            Bookshelf bookshelf3 = em.find(Bookshelf.class, 3);
            Bookshelf bookshelf4 = em.find(Bookshelf.class, 4);

            ShelfNew shelfNew1 = em.find(ShelfNew.class, 1);
            ShelfNew shelfNew2 = em.find(ShelfNew.class, 2);
            ShelfNew shelfNew3 = em.find(ShelfNew.class, 3);
            ShelfNew shelfNew4 = em.find(ShelfNew.class, 4);

            memberBookNew1.updateLocation(bookshelf1, shelfNew1);
            memberBookNew2.updateLocation(bookshelf2, shelfNew2);
            memberBookNew3.updateLocation(bookshelf3, shelfNew3);
            memberBookNew4.updateLocation(bookshelf4, shelfNew4);

        }

    }
}
