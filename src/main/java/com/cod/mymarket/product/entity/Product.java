package com.cod.mymarket.product.entity;

import com.cod.mymarket.base.entity.BaseEntity;
import com.cod.mymarket.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

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


}
