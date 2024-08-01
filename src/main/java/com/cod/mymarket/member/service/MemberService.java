package com.cod.mymarket.member.service;

import com.cod.mymarket.coupon.entity.Coupon;
import com.cod.mymarket.coupon.repository.CouponRepository;
import com.cod.mymarket.coupon.service.CouponService;
import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.member.repository.MemberRepository;
import com.cod.mymarket.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CouponService couponService;
    private final CouponRepository couponRepository;
    @Transactional
    public Member join(String username, String password, String email, String nickname, String address,String couponCode) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .address(address)
                .build();
        // 쿠폰 코드가 제공된 경우, 쿠폰을 추가
        if (couponCode != null) {
            Coupon coupon = couponRepository.findByCode(couponCode)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid coupon code: " + couponCode));
            if (member.getCoupons() == null) {
                member.setCoupons(new HashSet<>()); // 쿠폰 필드가 null인 경우 초기화
            }
            member.getCoupons().add(coupon);
        }
        memberRepository.save(member);
        return member;
    }

    @Transactional
    public void addCouponToMember(String username, Long couponId) {
        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        Optional<Coupon> couponOptional = couponService.findById(couponId);

        if (memberOptional.isPresent() && couponOptional.isPresent()) {
            Member member = memberOptional.get();
            Coupon coupon = couponOptional.get();
            member.getCoupons().add(coupon);
            memberRepository.save(member);
        } else {
            throw new RuntimeException("Member or Coupon not found");
        }
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

    public Member findByUserName(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);

        if (member.isPresent()) {
            return member.get();
        } else {
            throw new RuntimeException("member not found");
        }
    }

    // 사용자의 이름(또는 ID)으로 회원을 찾는 메서드
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
}