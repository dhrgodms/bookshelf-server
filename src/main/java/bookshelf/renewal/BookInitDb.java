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
            Shelf shelfA = new Shelf("shelfA", userA);
            em.persist(shelfA);
            Shelf shelfB = new Shelf("shelfB", userB);
            em.persist(shelfB);

            MemberShelf memberShelf1 = new MemberShelf(userA, shelfA);
            MemberShelf memberShelf2 = new MemberShelf(userB, shelfB);

            em.persist(memberShelf1);
            em.persist(memberShelf2);

            MemberShelf memberShelfAdd = new MemberShelf(userA, shelfB);
            em.persist(memberShelfAdd);
        }

        public void memberBookInit(){
            Member userA = em.find(Member.class, 1);
            Member userB = em.find(Member.class, 2);
            Book book1 = em.find(Book.class, 1);
            Book book2 = em.find(Book.class, 2);

            MemberBook memberBook1 = new MemberBook(userA, book1);
            em.persist(memberBook1);
            MemberBook memberBook2 = new MemberBook(userB, book2);
            em.persist(memberBook2);
        }

    }
}
