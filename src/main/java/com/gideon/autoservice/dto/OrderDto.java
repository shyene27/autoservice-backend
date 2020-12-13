package com.gideon.autoservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gideon.autoservice.enums.OrderStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    @JsonProperty("orderId")
    private Long orderId;

    @NonNull
    private LocalDateTime scheduledDate;

    private Duration taskDuration;

    private String taskDescription;

    private String customerName;
    private Long customerId;

    private String mechanicName;
    private Long mechanicId;

    private String carName;
    private Long carId;

    private OrderStatus orderStatus;

    private int orderPrice;

}
