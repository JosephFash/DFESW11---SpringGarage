package com.example.cardealership.service;

import com.example.cardealership.data.models.Car;
import com.example.cardealership.data.payloads.request.CarRequest;
import com.example.cardealership.data.payloads.response.MessageResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CarService {
    MessageResponse createCar(CarRequest carRequest);
    Optional<Car> updateCar(Integer CarId, CarRequest carRequest);
    void deleteCar(Integer CarId);
    Car getASingleCar(Integer CarId);
    List<Car> getAllCar();
    Car getCarById(long carId);

    Optional<Car> updateCar(string CarId);
}
