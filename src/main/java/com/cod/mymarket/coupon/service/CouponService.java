package com.cod.mymarket.coupon.service;

import com.cod.mymarket.coupon.entity.Coupon;
import com.cod.mymarket.coupon.repository.CouponRepository;
import com.cod.mymarket.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;

    @Transactional
    public Coupon createCoupon(String code, BigDecimal discount, LocalDate expiryDate) {
        Coupon coupon = Coupon.builder()
                .code(code)
                .discount(discount)
                .expiryDate(expiryDate)
                .build();

        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Optional<Coupon> findByCode(String code) {
        return couponRepository.findByCode(code);
    }

    @Transactional(readOnly = true)
    public Optional<Coupon> findById(Long id) {
        return couponRepository.findById(id);
    }

    @Transactional
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

}