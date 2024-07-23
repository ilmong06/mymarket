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
    private String detailImg;
    private String option;

    //적립금
    private int point;

    //썸네일 디테일 설명
    private String details;

    //썸네일 색상표와 디테일아이콘
    private String color;
    private String detailicon;

    @ManyToOne
    private Member member;

    @Enumerated(EnumType.STRING)
    private ProductType productType;
}