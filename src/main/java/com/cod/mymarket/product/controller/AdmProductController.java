package com.cod.mymarket.product.controller;

<<<<<<< HEAD
import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.product.entity.ProductType;
=======

>>>>>>> 9d81da79a630d2dcf0fd4dd69e6913d11289a8c2
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/product")
public class AdmProductController {
    private final ProductService productService;
    //쌤이랑 한 부분
//    @GetMapping("/create")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public String create() {
//        return "adm/product/create";
//    }
//
//    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public String createContent(
//            @RequestParam("title") String title, @RequestParam("description") String description,
//            @RequestParam("price") int price, @RequestParam("thumbnail") MultipartFile thumbnail
//
//    ) {
//        productService.create(title, description, price, thumbnail);
//
//        return "adm/product/create";
//    }

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
<<<<<<< HEAD
            @RequestParam("productType") String productType,
            @AuthenticationPrincipal Member member // 현재 로그인된 사용자를 가져옵니다.
    ) {
        // ProductType을 문자열로 받아서 ProductType enum으로 변환합니다.
        ProductType type;
        try {
            type = ProductType.valueOf(productType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid product type: " + productType);
        }

        // productService.create 메소드 호출
        productService.create(title, description, price, thumbnail, type);

        return "redirect:/adm/product/create"; // 상품 생성 후 다른 페이지로 리다이렉트
=======
            @RequestParam("type") String type // 추가된 파라미터
    ) {
        productService.create(title, description, price, thumbnail, type);
        return "redirect:/adm/product/create";
>>>>>>> 9d81da79a630d2dcf0fd4dd69e6913d11289a8c2
    }
}
