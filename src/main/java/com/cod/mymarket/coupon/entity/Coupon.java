package com.cod.mymarket.coupon.entity;

import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private BigDecimal discount; // 할인 비율 (예: 0.1은 10% 할인)

    private LocalDate expiryDate;

    @ManyToMany
    @JoinTable(
            name = "coupon_product",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public boolean isApplicableTo(Product product) {
        return products.contains(product);
    }

    public BigDecimal getDiscountAmount() {
        return discount;
    }
    @ManyToMany(mappedBy = "coupons")
    private Set<Member> members = new HashSet<>();
}