package com.example.cardealership.data.payloads.request;

import com.example.cardealership.data.models.Location;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
        

public class CarRequest {
    @NotBlank
    @NotNull
    private String manufacturer;
    @NotBlank
    @NotNull
    private String model;
    @NotBlank
    @NotNull
    private String registration;
    @NotBlank
    @NotNull
    private String type;
    @NotBlank
    @NotNull
    private double price;
    @NotBlank
    @NotNull
    @Enumerated(EnumType.STRING)
    private Location location;

    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getRegistration() {
        return registration;
    }
    public void setRegistration(String registration) {
        this.registration = registration;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
}
