package com.example.cardealership.web;

import com.example.cardealership.data.models.Car;
import com.example.cardealership.data.payloads.request.CarRequest;
import com.example.cardealership.data.payloads.response.MessageResponse;
import com.example.cardealership.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping("/all")
    public ResponseEntity<List<Car>> getAllCar () {
        List<Car> cars = carService.getAllCar();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
    @GetMapping("/find{id}")
    public ResponseEntity<Car> getCarById (@PathVariable("id") Integer id) {
        Car car = carService.getASingleCar(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<MessageResponse> addCar( @RequestBody CarRequest car) {
        MessageResponse newCar = carService.createCar(car);
        return new ResponseEntity<>(newCar, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponse> updateCar( @PathVariable Integer id, @RequestBody CarRequest car) {
        MessageResponse updateCar = carService.updateCar(id, car);
        return new ResponseEntity<>(updateCar, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id")
    public ResponseEntity<?> deleteCar(@PathVariable("id") Integer id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
