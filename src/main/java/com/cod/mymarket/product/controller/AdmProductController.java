package com.cod.mymarket.product.controller;


import com.cod.mymarket.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam("type") String type // 추가된 파라미터
    ) {
        productService.create(title, description, price, thumbnail, type);
        return "redirect:/adm/product/create";
    }
}
