package com.cod.mymarket.member.entity;

import com.cod.mymarket.base.entity.BaseEntity;
import com.cod.mymarket.coupon.entity.Coupon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String isActive;
    private String address;

    @ManyToMany
    @JoinTable(
            name = "member_coupon",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
    private Set<Coupon> coupons = new HashSet<>();

    private String point;


}
