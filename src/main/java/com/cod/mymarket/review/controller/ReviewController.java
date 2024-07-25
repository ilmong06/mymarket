package com.cod.mymarket.review.controller;

import com.cod.mymarket.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/review/create/{productId}")
    public String createReview(
            @PathVariable("productId") Long productId,
            @RequestParam("content") String content,
            @RequestParam("title") String title,
            @RequestParam("score") int score,
            @RequestParam("reviewImgs") List<MultipartFile> reviewImgs,
            Principal principal
    ) {
        reviewService.createReview(productId, content, title, score, reviewImgs, principal.getName());
        return String.format("redirect:/product/detail/%s", productId);
    }
}