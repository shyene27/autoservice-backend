package com.gideon.autoservice.entities;

import com.gideon.autoservice.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long orderId;

    @CreationTimestamp
    private LocalDateTime createdDate;

    private LocalDateTime scheduledDate;

    private Duration taskDuration;

    private String taskDescription;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId", nullable = false)
    private User customer;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "mechanicId", nullable = false)
    private User mechanic;

    @OneToOne(targetEntity = Car.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "carId", nullable = false)
    private Car car;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.NEW;

    private int orderPrice;
}
