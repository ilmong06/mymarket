package com.cod.mymarket.review.entity;

import com.cod.mymarket.base.entity.BaseEntity;
import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.product.entity.Product;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Review extends BaseEntity {
    private String content;
    private String title;
    private int score;
    private List<String> reviewImages;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Product product;

    private LocalDateTime createdDate;
}
