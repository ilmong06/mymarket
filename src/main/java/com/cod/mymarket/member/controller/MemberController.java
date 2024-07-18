package com.cod.mymarket.member.controller;

import com.cod.mymarket.member.form.MemberForm;
import com.cod.mymarket.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/socialjoin")
    public String showsocialjoin() {
        return "member/socialjoin";
    }

    @PostMapping("/socialjoin")
    public String socialjoin(@Valid MemberForm memberForm) {
        memberService.socialjoin(memberForm.getAddress());

        return "redirect:/member/login";
    }

}
