package com.krysinski.restapiw3.service;

import com.krysinski.restapiw3.model.Car;
import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getAllCars();
    Optional<Car> getCarById(Long id);
    List<Car> getCarsByColor(String color);

}
