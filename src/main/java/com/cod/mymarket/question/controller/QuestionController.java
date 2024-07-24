package com.cod.mymarket.question.controller;

import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.member.service.MemberService;
import com.cod.mymarket.product.entity.Product;
import com.cod.mymarket.product.service.ProductService;
import com.cod.mymarket.question.entity.Question;
import com.cod.mymarket.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ProductService productService;

    @GetMapping("/qlist")
    public String questionList(Model model, Principal principal) {
        List<Question> allQuestions = questionService.getAllQuestions();
        model.addAttribute("questions", allQuestions); // 모델에 질문 목록 추가
        return "question/qlist";
    }
    @PostMapping("/qlist")
    public String submitQuestion(
            @RequestParam("content") String content,
            @RequestParam("title") String title,
            @RequestParam("password") String password,
            Principal principal
    ) {
        Product product = null;
        Member member = memberService.findByUsername(principal.getName());

        questionService.create(product,member, content,title,password);

        return String.format("redirect:/question/qlist");
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(
            @PathVariable("id") Long id,
            @RequestParam("content") String content,
            @RequestParam("title") String title,
            @RequestParam("password") String password,
            Principal principal
    ) {
        Product product = productService.getProduct(id);
        Member member = memberService.findByUserName(principal.getName());

        questionService.create(product, member, content,title,password);

        return String.format("redirect:/product/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/view/{id}")
    public String viewQuestionWithPassword(
            @PathVariable("id") Long id,
            @RequestParam("password") String password,
            Model model
    ) {
        if (questionService.verifyPassword(id, password)) {
            Question question = questionService.getQuestion(id);
            model.addAttribute("viewedQuestion", question);
        } else {
            model.addAttribute("error", "Invalid password");
        }
        List<Question> allQuestions = questionService.getAllQuestions();
        model.addAttribute("questions", allQuestions); // 다시 질문 목록을 모델에 추가
        return "question/qlist";
    }
}