package bookshelf.renewal.repository;

import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberShelf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberShelfRepository extends JpaRepository<MemberShelf, Long>, MemberShelfCustomRepository {

    @Query("select ms from MemberShelf ms" +
            " join fetch ms.member" +
            " join fetch ms.shelf where ms.id = :id")
    Optional<MemberShelf> findMemberShelfById(@Param("id") Long id);

    Page<MemberShelf> findAllOwnByMemberAndShelfCreator(Member member, Member creator, Pageable pageable); //내가 만든 것만 가져오기


    Page<MemberShelf> findAllByMemberUsername(String username, Pageable pageable);

//    Page<MemberShelf> findAllByMember(String username, Pageable pageable);
}
