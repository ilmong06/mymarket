package com.cod.mymarket.cart.service;

import com.cod.mymarket.cart.entity.CartItem;
import com.cod.mymarket.cart.repository.CartRepository;
import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void add(Product product, Member member) {
        CartItem c = CartItem.builder()
            .product(product)
            .member(member)
            .build();

        cartRepository.save(c);
    }

    public List<CartItem> getList(Member member) {
        return cartRepository.findByMember(member);
    }
}
