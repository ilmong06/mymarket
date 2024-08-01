package com.cod.mymarket.home;

import com.cod.mymarket.product.entity.Product;
import com.cod.mymarket.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        List<Product> latestProducts = productService.getLatestProducts(20);  // 최신 상품 20개 가져오기
        model.addAttribute("products", latestProducts);
        return "home/main";
    }
}