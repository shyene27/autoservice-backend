package com.gideon.autoservice.config.translators;

import com.gideon.autoservice.dto.OrderDto;
import com.gideon.autoservice.entities.Car;
import com.gideon.autoservice.entities.Order;
import com.gideon.autoservice.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class OrderTranslator {

    public static OrderDto toDto(Order order){
        OrderDto orderDto = new OrderDto();

        orderDto.setOrderId(order.getOrderId());
        orderDto.setScheduledDate(order.getScheduledDate());
        orderDto.setTaskDuration(order.getTaskDuration());
        orderDto.setTaskDescription(order.getTaskDescription());
        orderDto.setCustomerName(order.getCustomer().getFullName());
        orderDto.setMechanicName(order.getMechanic().getFullName());
        orderDto.setCarName(order.getCar().getFullName());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setOrderPrice(order.getOrderPrice());

        return orderDto;
    }

    public static List<OrderDto> toDtoList(List<Order> orders){
        return orders.stream().map(OrderTranslator::toDto).collect(Collectors.toList());
    }

    public static Order fromDto(OrderDto orderDto, User customer, User mechanic, Car car){
       Order order = new Order();

       order.setOrderId(orderDto.getOrderId());
       order.setScheduledDate(orderDto.getScheduledDate());
       order.setTaskDuration(orderDto.getTaskDuration());
       order.setTaskDescription(orderDto.getTaskDescription());
       order.setCustomer(customer);
       order.setMechanic(mechanic);
       order.setCar(car);
       order.setOrderStatus(orderDto.getOrderStatus());
       order.setOrderPrice(orderDto.getOrderPrice());

       return order;
    }

}
