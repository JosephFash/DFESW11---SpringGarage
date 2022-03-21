package com.example.cardealership;

import com.example.cardealership.data.models.Car;
import com.example.cardealership.data.models.Location;
import com.example.cardealership.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Car;
import net.javaguides.springboot.service.CarService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest
@SpringBootTest
class CarDealershipApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenCarObject_whenCreateCar_thenReturnSavedCar() throws Exception{

        // given - precondition or setup
        Car car = Car.builder()
                .manufacturer("Ford")
                .model("Focus")
                .registration("GL21 AMR")
                .type("Hatchback")
                .price("£20325")
                .location(Location.valueOf("London"))
                .build();
        given(carService.saveCar(any(Car.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or behaviour being tested
        ResultActions response = mockMvc.perform(post("/api/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.manufacturer",
                        is(car.getManufacturer())))
                .andExpect(jsonPath("$.model",
                        is(car.getModel())))
                .andExpect(jsonPath("$.registration",
                        is(car.getRegistration())))
                .andExpect(jsonPath("$.type",
                        is(car.getType())))
                .andExpect(jsonPath("$.price",
                        is(car.getPrice())))
                .andExpect(jsonPath("$.location",
                        is(car.getLocation())));
    }

    //JUnit test for Get All cars REST API
    @Test
    public void giveListofCars_whenGetAllCars_thenReturnCarsList() throws Exception{
        // given - precondition or setup
        List<Car> listOfCars = new ArrayList<>();
        listOfCars.add(Car.builder().manufacturer("Ford").model("Focus").registration("GL21 AMR").type("Hatchback").price("£20325").location("London").build());
        listOfCars.add(Car.builder().manufacturer("Audi").model("RS6").registration("JO19 SFW").type("Estate").price("£104990").location("Bristol").build());
        given(carService.getAllCar()).willReturn(listOfCars);

        // when - action or the behaviour being tested
        ResultActions response = MockMvc.perform(get("/api/cars"));

        // then - verifying output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfCars)));
    }

    // positive scenario - valid car id
    // JUnit test for GET car by id REST API
    @Test
    public void givenCarId_whenGetCarById_thenReturnCarObject() throws Exception{
        // given - precondition or setup
        long carId = 1L;
        Car car = Car.builder()
                .manufacturer("Ford")
                .model("Focus")
                .registration("GL21 AMR")
                .type("Hatchback")
                .price("£20325")
                .location(Location.valueOf("London"))
                .build();
        given(carService.getCarById(carId)).willReturn(Optional.of(car));

        // when - action or behaviour being tested
        ResultActions response = mockMvc.perform(get("/api/cars/{id}", carId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.manufacturer",
                        is(car.getManufacturer())))
                .andExpect(jsonPath("$.model",
                        is(car.getModel())))
                .andExpect(jsonPath("$.registration",
                        is(car.getRegistration())))
                .andExpect(jsonPath("$.type",
                        is(car.getType())))
                .andExpect(jsonPath("$.price",
                        is(car.getPrice())))
                .andExpect(jsonPath("$.location",
                        is(car.getLocation())));
    }

    // negative scenario - valid car id
    //JUnit test for Get car by id REST API
    @Test
    public void givenInvalidCarID_whenGetCarById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        long carId = 1L;
        Car car = Car.builder()
                .manufacturer("Ford")
                .model("Focus")
                .registration("GL21 AMR")
                .type("Hatchback")
                .price("£20325")
                .location(Location.valueOf("London"))
                .build();
        given(carService.getCarById(carId)).willReturn(Optional.empty());

        // when - action or behaviour being tested
        ResultActions response = mockMvc.perform(get("/api/car/{id}", carId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    // JUnit test for update car REST API - positive scenario
        @Test
        public void givenUpdatedCar_whenUpdateCar_thenReturnUpdateCarObject() throws Exception{
        // given - precondition or setup
            long carId = 1L;
            Car savedCar = Car.builder()
                    .manufacturer("Ford")
                    .model("Focus")
                    .registration("GL21 AMR")
                    .type("Hatchback")
                    .price("£20325")
                    .location(Location.valueOf("London"))
                    .build();

            Car updatedCar = Car.builder()
                    .manufacturer("Ford")
                    .model("Fiesta")
                    .registration("GL21 AMR")
                    .type("Hatchback")
                    .price("£20325")
                    .location(Location.valueOf("Birmingham"))
                    .build();
            given(carService.getCarById(carId)).willReturn(Optional.of(savedCar));
            given(carService.updateCar(any(Car.class)))
                    .willAnswer((invocation)-> invocation.getArgument(0));

            // when - action or behaviour being tested
            ResultActions response = mockMvc.perform(put("/api/cars/{id}", carId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedCar)));

            // then - verify the output
            response.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.manufacturer",
                            is(car.getManufacturer())))
                    .andExpect(jsonPath("$.model",
                            is(car.getModel())))
                    .andExpect(jsonPath("$.registration",
                            is(car.getRegistration())))
                    .andExpect(jsonPath("$.type",
                            is(car.getType())))
                    .andExpect(jsonPath("$.price",
                            is(car.getPrice())))
                    .andExpect(jsonPath("$.location",
                            is(car.getLocation())));
        }

        // JUnit test for update car REST API - negative scenario
    @Test
    public void givenUpdatedCar_whenUpdatedCar_thenReturn404() throws Exception{
        // given - precondition or setup
        long carId = 1L;
        Car savedCar = Car.builder()
                .manufacturer("Ford")
                .model("Focus")
                .registration("GL21 AMR")
                .type("Hatchback")
                .price("£20325")
                .location("London")
                .build();

        Car updatedCar = Car.builder()
                .manufacturer("Ford")
                .model("Fiesta")
                .registration("GL21 AMR")
                .type("Hatchback")
                .price("£20325")
                .location("Birmingham")
                .build();
        given(carService.getCarById(carId)).willReturn(Optional.empty());
        given(carService.updateCar(any(car.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or behaviour being tested
        ResultActions response = mockMvc.perform(put("/api/cars/{id}", carId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCar)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // Junit test for delete car REST API
    @Test
    public void givenCarId_whenDeleteCar_thenReturn200() throws Exception{
        // given - precondition or setup
        long employee = 1L;
        willDoNothing().given(carService).deleteCar(carId);

        // when - action or behaviour being tested
        ResultActions response = mockMvc.perform(delete("/api/cars/{id}", carId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
