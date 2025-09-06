package bookshelf.renewal.controller;

import bookshelf.renewal.common.auth.JwtTokenProvider;
import bookshelf.renewal.domain.Member;
import bookshelf.renewal.dto.JoinDto;
import bookshelf.renewal.dto.LoginDto;
import bookshelf.renewal.exception.MemberNotExistException;
import bookshelf.renewal.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
        Member member = memberService.join(joinDto);
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            Member member = memberService.login(loginDto);
            String token = jwtTokenProvider.createToken(member.getId(), member.getRole().toString());

            HashMap<String, Object> memberInfo = new HashMap<>();
            memberInfo.put("token", token);
            memberInfo.put("email", member.getEmail());
            memberInfo.put("username", member.getUsername());
            memberInfo.put("id", member.getId());

            return new ResponseEntity<>(memberInfo, HttpStatus.OK);
        } catch (MemberNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
