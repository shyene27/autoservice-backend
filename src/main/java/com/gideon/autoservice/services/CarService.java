package com.gideon.autoservice.services;

import com.gideon.autoservice.config.translators.CarTranslator;
import com.gideon.autoservice.dao.CarRepository;
import com.gideon.autoservice.dao.UserRepository;
import com.gideon.autoservice.entities.Car;
import com.gideon.autoservice.entities.CarDto;
import com.gideon.autoservice.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    @Autowired
    CarRepository carRepository;
    @Autowired
    UserRepository userRepository;

    public List<CarDto> findAll() {

        List<Car> cars = carRepository.findAll();
        List<CarDto> carDtos = new ArrayList<>();

        for (Car car : cars){
            carDtos.add(CarTranslator.toDto(car));
        }

        return carDtos;
    }

    public CarDto save(CarDto carDto) {
        User user = userRepository.findById(carDto.getUserId()).get();

        Car createdCar = CarTranslator.fromDtoCreate(carDto,user);

        carRepository.save(createdCar);

        return CarTranslator.toDto(createdCar);
    }
}
