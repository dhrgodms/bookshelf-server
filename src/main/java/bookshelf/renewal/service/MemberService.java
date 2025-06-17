package bookshelf.renewal.service;

import bookshelf.renewal.domain.Member;
import bookshelf.renewal.exception.MemberNotExistException;
import bookshelf.renewal.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotExistException(id));
    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new MemberNotExistException(username));
    }



}
