package com.cod.mymarket.order.entity;

import com.cod.mymarket.base.entity.BaseEntity;
import com.cod.mymarket.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="product_order")
public class Order extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member buyer;
    private String name;
    private boolean isPaid;
    private boolean isCanceled;
    private boolean isRefunded;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItemList;

    // 필요한 필드 추가
    private String orderId;
    private Integer amount;
    private String paymentKey;

    // setter 추가
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }
}