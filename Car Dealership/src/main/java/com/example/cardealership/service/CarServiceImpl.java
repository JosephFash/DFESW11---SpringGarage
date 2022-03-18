package com.example.cardealership.service;

import com.example.cardealership.data.models.Car;
import com.example.cardealership.data.payloads.request.CarRequest;
import com.example.cardealership.data.payloads.response.MessageResponse;
import com.example.cardealership.data.repository.CarRepository;
import com.example.cardealership.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;

    @Override
    public MessageResponse createCar(CarRequest carRequest) {
        Car newCar = new Car();
        newCar.setManufacturer(carRequest.getManufacturer());
        newCar.setModel(carRequest.getModel());
        newCar.setRegistration(carRequest.getRegistration());
        newCar.setType(carRequest.getType());
        newCar.setPrice(carRequest.getPrice());
        newCar.setLocation(carRequest.getLocation());
        carRepository.save(newCar);
        return new MessageResponse("New Car created successfully");
    }

    @Override
    public Optional<Car> updateCar(Integer carId, CarRequest carRequest) throws ResourceNotFoundException{
        Optional<Car> car = carRepository.findById(carId);
        if (car.isEmpty()){
            throw new ResourceNotFoundException("Car", "id", carID);
    }
        else
        car.get().setManufacturer(carRequest.getManufacturer());
        car.get().setModel(carRequest.getModel());
        car.get().setRegistration(carRequest.getRegistration());
        car.get().setType(carRequest.getType());
        car.get().setPrice(carRequest.getPrice());
        car.get().setLocation(carRequest.getLocation());
        carRepository.save(car.get());
        return car;

}

@Override
public Car getASingleCar(Integer carId) throws ResourceNotFoundException{
        return carRepository.findById(carId).orElseThrow(() -> new ResourceNotFoundException("Car", "id", carId));
}

@Override
public List<Car> getAllCar() {
    return carRepository.findAll();
    }
}
@Override
public void deleteCar(Integer carId) throws ResourceNotFoundException {
    if (carRepository.getById(carId).getId().equals(carId)) {
        carRepository.deleteById(carId);
        }
    else throw new ResourceNotFoundException("Car", "id", carId);
    }
}

