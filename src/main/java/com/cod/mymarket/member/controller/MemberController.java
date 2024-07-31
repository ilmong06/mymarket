package com.cod.mymarket.member.controller;

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

import java.security.Principal;

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
        memberService.updateAddress(currentUsername, memberForm2.getAddress());

        return "redirect:/member/login";
    }
    @GetMapping("/mypage/{username}")
    public String showMypage(@PathVariable("username") String username, Principal principal, Model model) {
        // Fetch member information based on the username
        Member member = memberService.findByUsername(username);


        model.addAttribute("member", member);

        return "member/mypage";
    }

    @PostMapping("/mypage/{username}")
    public String mypage( @PathVariable("username") String username) {


        return "redirect:/member/mypage/" + username;
    }

}
