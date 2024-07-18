package com.cod.mymarket.member.service;

import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(String username, String password, String email, String nickname, String address, String coupon) {
        Member member = Member.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .email(email)
            .nickname(nickname)
            .address(address)
            .coupon(coupon)
            .build();

        memberRepository.save(member);

        return member;
    }

    public Member findByUserName(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);

        if (member.isPresent()) {
            return member.get();
        } else {
            throw new RuntimeException("member not found");
        }
    }

    public Member socialjoin(String address) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String username = oAuth2User.getAttribute("name"); // 소셜 로그인 제공자가 반환하는 사용자 이름 속성
        String email = oAuth2User.getAttribute("email"); // 소셜 로그인 제공자가 반환하는 이메일 속성
        String nickname = oAuth2User.getAttribute("nickname"); // 소셜 로그인 제공자가 반환하는 닉네임 속성
        Member member = Member.builder()
                .username(username)
                .email(email)
                .nickname(nickname)
                .address(address)
                .coupon(null)
                .build();

        memberRepository.save(member);

        return member;
    }
}
