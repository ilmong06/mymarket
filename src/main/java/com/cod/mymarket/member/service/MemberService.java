package com.cod.mymarket.member.service;

import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
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


    @Transactional
    public boolean isFirstSocialLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String nickname = oAuth2User.getAttribute("nickname");
            Optional<Member> existingMember = memberRepository.findByUsername(nickname);
            return existingMember.isEmpty(); // 존재하지 않는 경우에만 true 반환
        }
        return false;
    }
    @Transactional
    public void socialjoin(String address) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String username = oAuth2User.getAttribute("name");
            String email = oAuth2User.getAttribute("email");
            String nickname = oAuth2User.getAttribute("nickname");

            // 주소를 이용하여 회원가입 처리
            Member member = Member.builder()
                    .username(username)
                    .email(email)
                    .nickname(nickname)
                    .address(address)
                    .coupon(null) // 쿠폰 정보 초기화
                    .build();

            memberRepository.save(member);
        } else {
            throw new IllegalStateException("OAuth2User not found in SecurityContext");
        }
    }
}