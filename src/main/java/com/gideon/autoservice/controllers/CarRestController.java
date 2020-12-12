package com.gideon.autoservice.controllers;

import com.gideon.autoservice.config.translators.CarTranslator;
import com.gideon.autoservice.entities.Car;
import com.gideon.autoservice.dto.CarDto;
import com.gideon.autoservice.entities.User;
import com.gideon.autoservice.exceptions.CarAlreadyExistsException;
import com.gideon.autoservice.exceptions.CarNotFoundException;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import com.gideon.autoservice.services.CarService;
import com.gideon.autoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static com.gideon.autoservice.exceptions.HttpMessages.*;

@RestController
@RequestMapping("/cars")
public class CarRestController {


    @Autowired
    CarService carService;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<CarDto> findAll() {

        return CarTranslator.toDtoList(carService.findAll());
    }


    @GetMapping("/{id}")
    public CarDto getCarById(@PathVariable Long id) {
        try {
            return CarTranslator.toDto(carService.getCarById(id));
        } catch (CarNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_CAR_BY_ID, id));
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(FORBIDDEN, String.format(MESSAGE_ACCESS_DENIED));
        }
    }

    @PostMapping("/")
    public ResponseEntity<CarDto> saveCar(@RequestBody CarDto carDto) throws AccessDeniedException, UserNotFoundException {
        Car newCar;
        User user = userService.getUserById(carDto.getUserId());
        try {
            newCar = carService.save(CarTranslator.fromDto(carDto, user));
        } catch (CarAlreadyExistsException e) {
            return new ResponseEntity<>(CONFLICT);
        }

        return new ResponseEntity<>(CarTranslator.toDto(newCar), CREATED);
    }

    @PatchMapping("/{id}")
    public CarDto editCar(@RequestBody CarDto carDto) throws UserNotFoundException {
        try {
            User user = userService.getUserById(carDto.getUserId());
            Car modifiedCar = CarTranslator.fromDto(carDto, user);
            return CarTranslator.toDto(carService.editCar(modifiedCar));
        } catch (CarNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_CAR_BY_ID, carDto.getId()));
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(FORBIDDEN, String.format(MESSAGE_ACCESS_DENIED));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCarById(@PathVariable Long id) {

        try {
            carService.deleteCarById(id);
            return new ResponseEntity<>(NO_CONTENT);
        } catch (CarNotFoundException e){
            throw new ResponseStatusException(NOT_FOUND,String.format(MESSAGE_NO_CAR_BY_ID,id));
        }
    }
}
