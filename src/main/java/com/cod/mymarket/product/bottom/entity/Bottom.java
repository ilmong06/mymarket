package com.cod.mymarket.product.bottom.entity;

import com.cod.mymarket.product.entity.Product;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("BOTTOM")
@Getter
@SuperBuilder
@NoArgsConstructor
public class Bottom extends Product {
    // 추가 필드가 필요하면 여기에 추가
}