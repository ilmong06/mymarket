package com.cod.mymarket.order.service;

import com.cod.mymarket.order.entity.Order;
import com.cod.mymarket.order.entity.OrderItem;
import com.cod.mymarket.order.repository.OrderItemRepository;
import com.cod.mymarket.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    public void save(Order order) {
        orderRepository.save(order);
    }
    public void saveAll(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }
}