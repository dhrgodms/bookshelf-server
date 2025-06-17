package bookshelf.renewal.repository;

import bookshelf.renewal.domain.Member;
import bookshelf.renewal.domain.MemberShelf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberShelfRepository extends JpaRepository<MemberShelf, Long> {

//    @Query("select ms from MemberShelf ms join fetch ms.shelf join fetch ms.member")
    @EntityGraph(attributePaths = {"shelf"})
    Page<MemberShelf> findAllByMember(Member member, Pageable pageable);

//    @Query("select ms from MemberShelf ms join fetch ms.shelf where ms.shelf.creator = :member")
    @EntityGraph(attributePaths = {"shelf", "shelf.creator"})
    Page<MemberShelf> findAllOwnByMemberAndShelfCreator(Member member, Member creator, Pageable pageable); //내가 만든 것만 가져오기

    //    @Query("select ms from MemberShelf ms join fetch ms.shelf where ms.shelf.creator <> :member and ms.shelf.member = :member")
    @EntityGraph(attributePaths = {"member", "shelf", "shelf.creator"})
    Page<MemberShelf> findAllSubscribeByMemberUsernameAndShelfCreatorUsernameNot(String username, String creatorName, Pageable pageable);

    @EntityGraph(attributePaths = {"member", "shelf"})
    Page<MemberShelf> findAllByMemberUsername(String username, Pageable pageable);

}
