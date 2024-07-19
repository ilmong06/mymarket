package com.cod.mymarket.member.service;

import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public void updateAddress(String username, String address) {
        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setAddress(address);
            memberRepository.save(member);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}