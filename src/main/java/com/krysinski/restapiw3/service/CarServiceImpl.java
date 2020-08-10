package com.krysinski.restapiw3.service;

import com.krysinski.restapiw3.model.Car;
import com.krysinski.restapiw3.model.Color;
import com.krysinski.restapiw3.model.parse.ColorParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private List<Car> carsList;

    public CarServiceImpl(){
        this.carsList = new ArrayList<>();
        carsList.add(new Car(1L, "Opel", "Astra", Color.BLACK));
        carsList.add(new Car(2L, "Opel", "Vectra", Color.RED));
        carsList.add(new Car(3L, "Opel", "Corsa", Color.BLACK));
    }


    @Override
    public List<Car> getAllCars() {
        return carsList;
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        return carsList.stream().filter(car -> car.getId().equals(id)).findFirst();
    }

    @Override
    public List<Car> getCarsByColor(String colorString) {
        Color color = ColorParser.convertStringToColor(colorString);
        return  carsList.stream().filter(car -> car.getColor().equals(color))
        .collect(Collectors.toList());
    }

    @Override
    public boolean addCar(Car car){
        return carsList.add(car);
    }

    @Override
    public boolean replaceCar(Car newCar) {
        Optional<Car> foundCar = carsList.stream().filter(car -> car.getId().equals(newCar.getId())).findFirst();

        if (foundCar.isPresent()) {
            carsList.remove(foundCar.get());
            carsList.add(newCar);
            carsList.sort(Comparator.comparing(Car::getId));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCarMark(Long id, String mark) {
        Optional<Car> foundCar = carsList.stream().filter(car -> car.getId().equals(id)).findFirst();
        if (foundCar.isPresent()) {
            foundCar.get().setMark(mark);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCarModel(Long id, String model) {
        Optional<Car> foundCar = carsList.stream().filter(car -> car.getId().equals(id)).findFirst();
        if (foundCar.isPresent()) {
            foundCar.get().setModel(model);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCarColor(Long id, String color) {
        Optional<Car> foundCar = carsList.stream().filter(car -> car.getId().equals(id)).findFirst();
        if (foundCar.isPresent()) {
            foundCar.get().setColor(color);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeCar(Long id){
        Optional<Car> foundCar = carsList.stream().filter(car -> car.getId().equals(id)).findFirst();
        if (foundCar.isPresent()){
            carsList.remove(foundCar.get());
            return true;
        }
        return false;
    }

}
