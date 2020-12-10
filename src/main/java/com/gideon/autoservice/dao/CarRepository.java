package com.gideon.autoservice.dao;

import com.gideon.autoservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {
    Optional<Car> findByVin(String vin);

    Optional<Object> findByCarIdAndIsDeleted(Long id, boolean b);

    List<Car> findByIsDeleted(boolean b);
}
