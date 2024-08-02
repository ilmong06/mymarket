package com.cod.mymarket.coupon.controller;

import com.cod.mymarket.coupon.entity.Coupon;
import com.cod.mymarket.coupon.service.CouponService;
import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    private final MemberService memberService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("coupon", new com.cod.mymarket.coupon.controller.CouponForm());
        return "coupon/create";
    }

    @PostMapping("/create")
    public String createCoupon(@Valid @ModelAttribute com.cod.mymarket.coupon.controller.CouponForm couponForm, BindingResult result) {
        if (result.hasErrors()) {
            return "coupon/create";
        }

        couponService.createCoupon(
                couponForm.getCode(),
                couponForm.getDiscount(),
                couponForm.getExpiryDate()
        );

        return "redirect:/coupon/list";
    }

    @GetMapping("/list")
    public String listCoupons(Model model) {
        model.addAttribute("coupons", couponService.findAll());
        return "coupon/list";
    }

    @GetMapping("/{id}")
    public String viewCoupon(@PathVariable Long id, Model model) {
        Optional<Coupon> coupon = couponService.findById(id);
        if (coupon.isPresent()) {
            model.addAttribute("coupon", coupon.get());
            return "coupon/view";
        } else {
            return "redirect:/coupon/list";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return "redirect:/coupon/list";
    }

    @GetMapping("/couponlist")
    @PreAuthorize("isAuthenticated()")
    public String showcouponlist(Principal principal, Model model) {
        String username = principal.getName();
        Member member = memberService.findByUsername(username);

        List<Coupon> coupons = member.getCoupons().stream().toList();

        model.addAttribute("member", member);
        model.addAttribute("coupons", coupons);

        return "coupon/couponlist";
    }
}