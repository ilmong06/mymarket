package com.cod.mymarket.product.controller;

import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.product.entity.ProductType;
import com.cod.mymarket.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/product")
public class AdmProductController {
    private final ProductService productService;

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create() {
        return "adm/product/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createContent(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") int price,
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @RequestParam("detailImgs") List<MultipartFile> detailImgs,
            @RequestParam("productType") String productType,
            @RequestParam(value = "option", required = false) List<String> option,
            @RequestParam("point") int point,
            @RequestParam("details") String details,
            @RequestParam("colorImages") List<MultipartFile> colorImages,
            @RequestParam("detailicon") MultipartFile detailicon,
            @AuthenticationPrincipal Member member
    ) {

        ProductType type;
        try {
            type = ProductType.valueOf(productType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid product type: " + productType);
        }


        productService.create(title, description, price, thumbnail, detailImgs, type, option, point,details,colorImages,detailicon);

        return "redirect:/adm/product/create"; // 상품 생성 후 다른 페이지로 리다이렉트
    }
}