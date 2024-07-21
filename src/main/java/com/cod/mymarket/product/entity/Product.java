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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type")
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

<<<<<<< HEAD
    @Enumerated(EnumType.STRING)
    private ProductType productType;
=======
    @Transient
    private String productType;
    // productType을 설정하는 메서드
    public void setProductType(String productType) {
        this.productType = productType;
    }
>>>>>>> 9d81da79a630d2dcf0fd4dd69e6913d11289a8c2
}