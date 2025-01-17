package com.cod.mymarket.question.entity;

import com.cod.mymarket.base.entity.BaseEntity;
import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.product.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Question extends BaseEntity {
    private String content;
    private String title;
    private String password;

    @ManyToOne
    private Member member;
    @ManyToOne
    private Product product;

    private LocalDateTime createdDate;
}
