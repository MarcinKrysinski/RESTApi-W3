package com.krysinski.restapiw3.service;

import com.krysinski.restapiw3.model.Car;
import com.krysinski.restapiw3.model.Colors;
import com.krysinski.restapiw3.model.parse.ColorParser;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private List<Car> carsList;

    public CarServiceImpl(){
        this.carsList = new ArrayList<>();
        carsList.add(new Car(1L, "Opel", "Astra", Colors.BLACK));
        carsList.add(new Car(2L, "Opel", "Vectra", Colors.RED));
        carsList.add(new Car(3L, "Opel", "Corsa", Colors.BLACK));
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
    public List<Car> getCarsByColor(String colorString) throws NullPointerException {
        Colors color = ColorParser.convertStringToColor(colorString);
        return  carsList.stream().filter(car -> car.getColor().equals(color))
        .collect(Collectors.toList());

    }


}
