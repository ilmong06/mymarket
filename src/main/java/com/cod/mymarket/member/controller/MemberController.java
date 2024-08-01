package com.cod.mymarket.member.controller;

import com.cod.mymarket.coupon.entity.Coupon;
import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.member.form.MemberForm;
import com.cod.mymarket.member.form.MemberForm2;
import com.cod.mymarket.member.service.MemberService;
import com.cod.mymarket.order.entity.Order;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Valid MemberForm memberForm) {
        memberService.join(memberForm.getUsername(), memberForm.getPassword(), memberForm.getEmail(), memberForm.getNickname(), memberForm.getAddress(), memberForm.getAddress());

        return "redirect:/member/login";
    }
    @GetMapping("/social_join")
    public String showsocialjoin() {
        return "member/social_join";
    }

    @PostMapping("/social_join")
    public String socialjoin(@Valid MemberForm2 memberForm2) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Update the address for the authenticated user
        memberService.updateAddress(currentUsername, memberForm2.getAddress(),memberForm2.getNickname(),memberForm2.getEmail());

        return "redirect:/member/login";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public RedirectView redirectToUserPage(Principal principal) {
        String username = principal.getName(); // 현재 인증된 사용자의 이름

        // 사용자 이름이 유효한 경우, 해당 사용자 페이지로 리다이렉트
        if (username != null && !username.isEmpty()) {
            return new RedirectView("/member/mypage/" + username);
        } else {
            return new RedirectView("/member/login"); // 사용자 이름이 없으면 로그인 페이지로 리디렉션
        }
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage/{username}")
    public String showMypage(@PathVariable("username") String username, Model model) {
        Member member = memberService.findByUsername(username);
        model.addAttribute("member", member);
        return "member/mypage"; // Thymeleaf 템플릿 파일
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/mypage/{username}")
    public String mypage( @PathVariable("username") String username) {


        return "redirect:/member/mypage/" + username;
    }
    @GetMapping("/check")
    @PreAuthorize("isAuthenticated()")
    public String checkMemberInfo(Principal principal, Model model) {
        String username = principal.getName();
        Member member = memberService.findByUsername(username);

        List<Coupon> coupons = member.getCoupons().stream().toList();

        model.addAttribute("member", member);
        model.addAttribute("coupons", coupons);

        return "member/check";
    }
}
