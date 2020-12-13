package com.gideon.autoservice.controllers;

import com.gideon.autoservice.config.translators.OrderTranslator;
import com.gideon.autoservice.dto.OrderDto;
import com.gideon.autoservice.entities.Car;
import com.gideon.autoservice.entities.Order;
import com.gideon.autoservice.entities.User;
import com.gideon.autoservice.exceptions.CarNotFoundException;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import com.gideon.autoservice.services.CarService;
import com.gideon.autoservice.services.OrderService;
import com.gideon.autoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

    @Autowired
    CarService carService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @GetMapping("/")
    public List<OrderDto> findAll(){
        return OrderTranslator.toDtoList(orderService.findAll());
    }



    @PostMapping("/")
    public ResponseEntity<OrderDto> saveOrder(@RequestBody OrderDto orderDto) throws AccessDeniedException, UserNotFoundException, CarNotFoundException {
        User customer = userService.getUserById(orderDto.getCustomerId());
        User mechanic = userService.getUserById(orderDto.getMechanicId());
        Car car = carService.getCarById(orderDto.getCarId());
        Order order = orderService.save(OrderTranslator.fromDto(orderDto,customer,mechanic,car));

        return new ResponseEntity<>(OrderTranslator.toDto(order), CREATED);
    }
}
