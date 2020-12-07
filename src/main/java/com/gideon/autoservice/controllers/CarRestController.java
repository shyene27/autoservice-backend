package com.gideon.autoservice.controllers;

import com.gideon.autoservice.entities.CarDto;
import com.gideon.autoservice.entities.UserDto;
import com.gideon.autoservice.exceptions.CarAlreadyExistsException;
import com.gideon.autoservice.exceptions.UserAlreadyExistsException;
import com.gideon.autoservice.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/cars")
public class CarRestController {

    @Autowired
    CarService carService;

    @GetMapping("/")
    public List<CarDto> findAll(){
        return carService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<CarDto> saveCar(@RequestBody CarDto carDto) {
        CarDto createdCar;

        try {
            createdCar = carService.save(carDto);
        } catch (CarAlreadyExistsException e) {
            return new ResponseEntity<>(CONFLICT);
        }

        return new ResponseEntity<>(createdCar, CREATED);
    }

}
