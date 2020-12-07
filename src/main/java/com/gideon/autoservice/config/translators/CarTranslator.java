package com.gideon.autoservice.config.translators;

import com.gideon.autoservice.dao.UserRepository;
import com.gideon.autoservice.entities.Car;
import com.gideon.autoservice.entities.CarDto;
import com.gideon.autoservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

public class CarTranslator {

    @Autowired
    static
    UserRepository userRepository;

    public static CarDto toDto(Car car){
        CarDto carDto = new CarDto();

        carDto.setId(car.getCarId());
        carDto.setVin(car.getVin());
        carDto.setCarData(car.getMake()+" "+car.getModel()+" "+car.getYear());
        carDto.setUserId((car.getUser()).getUserId());

        return carDto;
    }

    public static Car fromDtoCreate(CarDto carDto, User user){
        Car car = new Car();

        car.setCarId(carDto.getId());
        car.setVin(carDto.getVin());
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        car.setUser(user);

        return car;
    }

    public static Car fromDtoUpdate(CarDto carDto, Car currentCar) {
        Car car = new Car();

        if(carDto.getVin()!=null) currentCar.setVin(carDto.getVin());
        if(carDto.getMake()!=null) currentCar.setMake(carDto.getMake());
        if(carDto.getModel()!=null) currentCar.setModel(carDto.getModel());
        if(carDto.getYear()!=null) currentCar.setYear(carDto.getYear());



        return car;
    }
}
