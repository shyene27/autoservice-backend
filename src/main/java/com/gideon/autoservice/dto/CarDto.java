package com.gideon.autoservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDto {

    @JsonProperty("carId")
    private Long id;

    @NonNull
    private String vin;

    private String make;

    private String model;

    private Short year;

    private String carData;

    @NonNull
    private Long userId;
}
