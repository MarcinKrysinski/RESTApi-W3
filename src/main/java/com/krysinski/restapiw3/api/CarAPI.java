package com.krysinski.restapiw3.api;

import com.krysinski.restapiw3.service.CarService;
import com.krysinski.restapiw3.model.Car;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/cars", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CarAPI {

    private CarService carService;

    public CarAPI(CarService carService) {
        this.carService = carService;
    }

    private void addLinkToCar(Car car){
        car.add(linkTo((CarAPI.class)).slash(car.getId()).withSelfRel());
    }

    private Link addLink(Long id){
        return linkTo(CarAPI.class).slash(id).withSelfRel();
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Car>> getCars() {
        List<Car> allCars = carService.getAllCars();
        allCars.forEach(car -> car.add(addLink(car.getId()).withSelfRel()));
        Link link = linkTo(CarAPI.class).withSelfRel();
        CollectionModel<Car> carCollectionModel = new CollectionModel<>(allCars, link);
        return new ResponseEntity<>(carCollectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") Long id) {
        Optional<Car> carById = carService.getCarById(id);
        return carById.map(car -> {
            addLinkToCar(car);
            return ResponseEntity.ok(car);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<EntityModel<Car>> getCarById(@PathVariable Long id) {
//        Link link = addLink(id);
//        Optional<Car> carById = carService.getCarById(id);
//        if (carById.isPresent()) {
//            EntityModel<Car> carEntityModel = new EntityModel<>(carById.get(), link);
//            return new ResponseEntity<>(carEntityModel, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping("/color/{color}")
    public ResponseEntity<CollectionModel<Car>> getCarsByColor(@PathVariable String color) {
        List<Car> carsByColor = carService.getCarsByColor(color);
        if (carsByColor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        carsByColor.forEach(car -> car.add(addLink(car.getId()).withSelfRel()));
        carsByColor.forEach(car -> car.add(linkTo(CarAPI.class).withRel("allColors")));
        Link link = linkTo(CarAPI.class).withSelfRel();
        CollectionModel<Car> carCollectionModel = new CollectionModel<>(carsByColor, link);
        return new ResponseEntity<>(carCollectionModel, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car car) {
        boolean addCar = carService.addCar(car);
        if (addCar) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PutMapping
    public ResponseEntity replaceCar(@RequestBody Car newCar) {
        boolean replaceCar = carService.replaceCar(newCar);
        if (replaceCar) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping("/mark/{id}/{mark}")
    public ResponseEntity updateCarMark(@PathVariable Long id, @PathVariable String mark) {
        boolean updateCarMark = carService.updateCarMark(id, mark);
        if (updateCarMark) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/mark/{id}/{model}")
    public ResponseEntity updateCarModel(@PathVariable Long id, @PathVariable String model) {
        boolean updateCarModel = carService.updateCarModel(id, model);
        if (updateCarModel) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/color/{id}/{color}")
    public ResponseEntity updateCarColor(@PathVariable Long id, @PathVariable String color) {
        boolean updateCarColor = carService.updateCarColor(id, color);
        if (updateCarColor) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCarById(@PathVariable Long id) {
        boolean deleteCarById = carService.removeCar(id);
        if (deleteCarById) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
