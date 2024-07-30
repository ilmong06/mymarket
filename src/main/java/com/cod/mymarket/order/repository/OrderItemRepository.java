package com.cod.mymarket.order.repository;

import com.cod.mymarket.order.entity.Order;
import com.cod.mymarket.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
