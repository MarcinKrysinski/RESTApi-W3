package com.krysinski.restapiw3.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krysinski.restapiw3.model.Car;
import com.krysinski.restapiw3.model.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.ojai.util.Values.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CarAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("Should 3 cars on the list and right models")
    void getCars() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].model").value("Astra"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].model").value("Vectra"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].model").value("Corsa"));
    }

    @Test
    @DisplayName("Should find car by id test")
    void shouldFindCarByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/{id}", 2L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark").value("Opel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Vectra"));
    }

    @Test
    @DisplayName("Should not find car by id test")
    void shouldNotFindCarByIdTest()  throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cars/{id}", 4L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should find car by color test")
    void shouldFindCarByColor() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cars/color/{color}", Color.RED)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Should not find car by color test")
    void shouldNotFindCarByColor() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cars/color/{color}", Color.BLUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("Should add car test")
    void shouldSaveCarTest() throws Exception {
        Car car = new Car(4L, "Opel", "Mokka", Color.BLUE);
        String jsonRequest = objectMapper.writeValueAsString(car);
        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



    @Test
    @DisplayName("Should update car color with given id test")
    void shouldUpdateCarColorWithGivenIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/cars/color/{id}/{color}", 1L, Color.RED)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("Should not update car's color with given id test")
    void shouldNotUpdateCarColorWithGivenIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/cars/{carId}/color/{newColor}", 5L, Color.BLUE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update car's mark with given id test")
//    @DirtiesContext
    void shouldUpdateCarMarkWithGivenIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/cars/mark/{id}/{mark}", 2L, "BMW")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should delete car by given id test")
    void shouldDeleteCarByGivenIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/{id}", 3L))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Should not delete car when car with given id not exist, should return not fund status test")
    void shouldNotDeleteCarWhenCarWithGivenIdNotExistShouldReturnNotFundStatusTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/{id}", 10L))
                .andExpect(status().isNotFound());
    }
}
