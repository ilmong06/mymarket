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
    private String orderName;
    private String productIds;

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
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    // Getters and Setters

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


}