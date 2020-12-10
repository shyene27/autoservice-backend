package com.gideon.autoservice.services;

import com.gideon.autoservice.config.translators.CarTranslator;
import com.gideon.autoservice.dao.CarRepository;
import com.gideon.autoservice.dao.UserRepository;
import com.gideon.autoservice.entities.Car;
import com.gideon.autoservice.exceptions.CarAlreadyExistsException;
import com.gideon.autoservice.exceptions.CarNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    @Autowired
    CarRepository carRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LoggedUserValidationService validationService;

    public List<Car> findAll() {

        return carRepository.findByIsDeleted(false);
    }


    public Car getCarById(Long id) throws CarNotFoundException, AccessDeniedException {

        Car car = (Car) carRepository.findByCarIdAndIsDeleted(id, false)
                .orElseThrow(() -> new CarNotFoundException());

        validationService.validateUserAccess(car);

        return car;
    }


    public Car save(Car newCar) {

        carRepository.findByVin(newCar.getVin()).ifPresent(s -> {
            Car presentCar = carRepository.findByVin(newCar.getVin()).get();
            presentCar.setDeleted(false);
            throw new CarAlreadyExistsException();
        });

        return carRepository.save(newCar);
    }

    public Car editCar(Car modifiedCar) throws CarNotFoundException, AccessDeniedException {
        Car currentCar = (Car) carRepository.findByCarIdAndIsDeleted(modifiedCar.getCarId(), false)
                .orElseThrow(() -> new CarNotFoundException());

        validationService.validateUserAccess(modifiedCar);

        return carRepository.save(CarTranslator.updateCarUtil(modifiedCar, currentCar));

    }

    public void deleteCarById(Long id) throws CarNotFoundException {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException());
        car.setDeleted(true);
        carRepository.save(car);
    }
}
