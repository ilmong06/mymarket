package com.cod.mymarket.product.controller;

import com.cod.mymarket.product.entity.Product;
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
            @RequestParam(value="kw", defaultValue="") String kw
    ) {
        Page<Product> paging = productService.getList(page, kw);

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "product/toplist";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProduct(id);

        model.addAttribute("product", product);
        System.out.println(product.toString());

        return "product/detail";
    }
}
