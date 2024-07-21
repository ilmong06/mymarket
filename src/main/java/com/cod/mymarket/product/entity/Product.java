package com.cod.mymarket.product.entity;

import com.cod.mymarket.base.entity.BaseEntity;
import com.cod.mymarket.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    private String title;
    private String description;
    private int price;
    private int hitCount;
    private String isActive;
    private String thumbnailImg;

    @ManyToOne
    private Member member;

    @Enumerated(EnumType.STRING)
    private ProductType productType;
}