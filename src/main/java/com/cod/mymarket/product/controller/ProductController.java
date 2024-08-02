package com.cod.mymarket.product.controller;

import com.cod.mymarket.product.entity.Product;
import com.cod.mymarket.product.entity.ProductType;
import com.cod.mymarket.product.service.ProductService;
import com.cod.mymarket.question.entity.Question;
import com.cod.mymarket.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final QuestionService questionService;

    @GetMapping("/toplist")
    public String toplist(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "kw", defaultValue = "") String kw
    ) {
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
    @GetMapping("/bslist")
    public String bslist(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "kw", defaultValue = "") String kw
    ) {
        Page<Product> paging;
        if (kw.isEmpty()) {
            paging = productService.getByProductType(page, ProductType.BS);
        } else {
            paging = productService.getByProductType(page, ProductType.BS);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("type", "BS"); // 추가: 현재 필터링된 타입을 HTML 템플릿에 전달
        return "product/bslist";
    }
    @GetMapping("/dresslist")
    public String dresslist(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "kw", defaultValue = "") String kw
    ) {
        Page<Product> paging;
        if (kw.isEmpty()) {
            paging = productService.getByProductType(page, ProductType.DRESS);
        } else {
            paging = productService.getByProductType(page, ProductType.DRESS);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("type", "DRESS"); // 추가: 현재 필터링된 타입을 HTML 템플릿에 전달
        return "product/dresslist";
    }
    @GetMapping("/outerlist")
    public String outerlist(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "kw", defaultValue = "") String kw
    ) {
        Page<Product> paging;
        if (kw.isEmpty()) {
            paging = productService.getByProductType(page, ProductType.OUTER);
        } else {
            paging = productService.getByProductType(page, ProductType.OUTER);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("type", "OUTER"); // 추가: 현재 필터링된 타입을 HTML 템플릿에 전달
        return "product/outerlist";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model, Principal principal) {
        productService.incrementHitCount(id);
        Product product = productService.getProduct(id);
        String currentUsername = principal != null ? principal.getName() : "";
        List<Question> allQuestions = questionService.getQuestionsByProduct(id);

        model.addAttribute("allQuestions", allQuestions);
        model.addAttribute("currentUsername", currentUsername);
        model.addAttribute("product", product);
        System.out.println(product.toString());

        return "product/detail";
    }
    @GetMapping("/new")
    public String shownew(Model model) {
        List<Product> latestProducts = productService.getLatestProducts(30);
        model.addAttribute("products", latestProducts);
        return "product/new";
    }
    @GetMapping("/best")
    public String bestList(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        // Pageable 객체를 생성하여 페이지네이션을 설정합니다.
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "hitCount"));

        // 서비스에서 페이지네이션된 데이터를 조회합니다.
        Page<Product> paging = productService.getProductsSortedByHitCount(pageable);

        // 모델에 데이터를 추가합니다.
        model.addAttribute("paging", paging);
        model.addAttribute("type", "BEST");

        // HTML 템플릿의 경로를 반환합니다.
        return "product/best";
    }
}
