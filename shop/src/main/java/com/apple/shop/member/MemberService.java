package com.apple.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public void saveMem(String displayName,String username, String password)throws Exception{
        Optional<Member> result = memberRepository.findByUsername(username);
        if(result.isPresent()){
            throw new Exception("존재하는 아이디");
        }
        if(username.length()<5 || password.length()<5){
            throw new Exception("너무 짧음");
        }
        Member member = new Member();
        member.setDisplayName(displayName);
        member.setUsername(username);
        String hash = passwordEncoder.encode(password);
        member.setPassword(hash);
        memberRepository.save(member);
    }
}
