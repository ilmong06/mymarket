package com.cod.mymarket.coupon.controller;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CouponForm {
    @NotBlank
    private String code;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal discount;

    @NotNull
    private LocalDate expiryDate;
}