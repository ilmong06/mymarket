package com.cod.mymarket.order.entity;

import com.cod.mymarket.base.entity.BaseEntity;
import com.cod.mymarket.product.entity.Product;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderItem extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;  // 필수 필드 설정
    private LocalDateTime payDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String title;
    private int price;
    private String thumbnailImg;

    @ElementCollection
    private List<String> option; // 상품 옵션이 있을 경우
}
