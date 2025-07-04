package bookshelf.renewal.service;

import bookshelf.renewal.domain.Member;
import bookshelf.renewal.dto.JoinDto;
import bookshelf.renewal.dto.LoginDto;
import bookshelf.renewal.exception.MemberNotExistException;
import bookshelf.renewal.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotExistException(id));
    }

    public Member join(JoinDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElse(null);

        //Member 생성
        if(member == null){
            Member newMember = new Member(dto.getName(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
             member = memberRepository.save(newMember);
        } else{
            throw new MemberNotExistException(dto.getEmail());
        }

        return member;
    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new MemberNotExistException(username));
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberNotExistException(email));
    }


    public Member login(LoginDto loginDto) {
        Optional<Member> optMember = memberRepository.findByEmail(loginDto.getEmail());
        if (!optMember.isPresent()) {
            throw new MemberNotExistException(loginDto.getEmail());
        }
        Member member = optMember.get();
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("Password not matched");
        }

        return member;
    }
}
