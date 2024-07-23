package com.cod.mymarket.product.controller;

import com.cod.mymarket.product.entity.Product;
import com.cod.mymarket.product.entity.ProductType;
import com.cod.mymarket.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/toplist")
    public String toplist(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "kw", defaultValue = "") String kw
    ) {
        // ProductType.TOP으로 필터링된 상품만 반환
        Page<Product> paging;
        if (kw.isEmpty()) {
            paging = productService.getByProductType(page, ProductType.TOP);
        } else {
            paging = productService.getByProductType(page, ProductType.TOP);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("type", "TOP"); // 추가: 현재 필터링된 타입을 HTML 템플릿에 전달
        return "product/toplist";
    }

    @GetMapping("/bottomlist")
    public String bottomlist(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "kw", defaultValue = "") String kw
    ) {
        // ProductType.TOP으로 필터링된 상품만 반환
        Page<Product> paging;
        if (kw.isEmpty()) {
            paging = productService.getByProductType(page, ProductType.BOTTOM);
        } else {
            paging = productService.getByProductType(page, ProductType.BOTTOM);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("type", "BOTTOM"); // 추가: 현재 필터링된 타입을 HTML 템플릿에 전달
        return "product/bottomlist";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        productService.incrementHitCount(id);
        Product product = productService.getProduct(id);

        model.addAttribute("product", product);
        System.out.println(product.toString());

        return "product/detail";
    }
}
