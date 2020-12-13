package com.gideon.autoservice.config.translators;

import com.gideon.autoservice.entities.Car;
import com.gideon.autoservice.dto.CarDto;
import com.gideon.autoservice.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class CarTranslator {


    public static CarDto toDto(Car car) {
        CarDto carDto = new CarDto();

        carDto.setId(car.getCarId());
        carDto.setVin(car.getVin());
        carDto.setCarData(car.getMake() + " " + car.getModel() + " " + car.getYear());
        carDto.setUserId((car.getUser()).getUserId());

        return carDto;
    }

    public static List<CarDto> toDtoList(List<Car> cars) {

        return cars.stream().map(CarTranslator::toDto).collect(Collectors.toList());
    }

    public static Car fromDto(CarDto carDto, User user) {
        Car car = new Car();

        car.setCarId(carDto.getId());
        car.setVin(carDto.getVin());
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        car.setUser(user);

        return car;
    }

}
