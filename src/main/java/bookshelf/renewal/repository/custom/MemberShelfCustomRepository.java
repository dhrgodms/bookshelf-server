package bookshelf.renewal.repository.custom;

import bookshelf.renewal.dto.MemberShelfDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberShelfCustomRepository {
    Page<MemberShelfDto> findAllNotCreator(String username,Pageable pageable);

    Page<MemberShelfDto> findAllByMember(String username, Pageable pageable);

    Page<MemberShelfDto> findAllOwnShelves(String username, Pageable pageable);
}
