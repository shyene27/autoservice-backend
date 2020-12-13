package com.gideon.autoservice.services;

import com.gideon.autoservice.dao.OrderRepository;
import com.gideon.autoservice.entities.Order;
import com.gideon.autoservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order save(Order order) {
        order.setOrderStatus(OrderStatus.NEW);
        return orderRepository.save(order);
    }
}
