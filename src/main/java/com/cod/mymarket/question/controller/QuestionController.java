package com.cod.mymarket.question.controller;

import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.member.service.MemberService;
import com.cod.mymarket.question.entity.Question;
import com.cod.mymarket.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    private final MemberService memberService;

    @GetMapping("/qlist")
    public String questionList(Model model, Principal principal) {
        List<Question> allQuestions = questionService.getAllQuestions();


        return "question/qlist";
    }

    @PostMapping("/qlist")
    public String submitQuestion(
            @RequestParam("content") String content,
            Principal principal
    ) {
        Member member = memberService.findByUsername(principal.getName());

        questionService.create(member, content);

        return String.format("redirect:/question/qlist");
    }
}