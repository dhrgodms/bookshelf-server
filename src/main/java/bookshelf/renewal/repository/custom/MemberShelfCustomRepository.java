package bookshelf.renewal.repository.custom;

import bookshelf.renewal.dto.MemberShelfDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberShelfCustomRepository {
    Page<MemberShelfDto> findAllNotCreator(Long memberId,Pageable pageable);

    Page<MemberShelfDto> findAllByMember(Long memberId, Pageable pageable);

    Page<MemberShelfDto> findAllOwnShelves(Long memberId, Pageable pageable);
}
