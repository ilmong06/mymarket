package com.cod.mymarket.cart.repository;


import com.cod.mymarket.cart.entity.CartItem;
import com.cod.mymarket.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByMember(Member member);
}
