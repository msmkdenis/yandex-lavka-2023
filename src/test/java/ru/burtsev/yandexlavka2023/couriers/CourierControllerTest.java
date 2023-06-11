package ru.burtsev.yandexlavka2023.couriers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.burtsev.yandexlavka2023.couriers.controller.CourierController;
import ru.burtsev.yandexlavka2023.couriers.dto.CourierDto;
import ru.burtsev.yandexlavka2023.couriers.dto.CreateCourierDto;
import ru.burtsev.yandexlavka2023.couriers.dto.CreateCourierRequest;
import ru.burtsev.yandexlavka2023.couriers.dto.CreateCouriersResponse;
import ru.burtsev.yandexlavka2023.couriers.entity.CourierType;
import ru.burtsev.yandexlavka2023.facade.DeliveryFacade;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourierController.class)
@AutoConfigureMockMvc
public class CourierControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DeliveryFacade deliveryFacade;

    private CreateCourierRequest courierRequest;
    private CreateCouriersResponse couriersResponse;

    @BeforeEach
    void init() {
        CreateCourierDto createCourierDto_1 = CreateCourierDto.builder()
                .courierType(CourierType.FOOT)
                .regions(Set.of(1, 2, 3))
                .workingHours(Set.of("12:00-13:00", "14:00-15:00"))
                .build();
        CreateCourierDto createCourierDto_2 = CreateCourierDto.builder()
                .courierType(CourierType.BIKE)
                .regions(Set.of(2, 3, 4))
                .workingHours(Set.of("11:00-12:00", "14:00-15:00"))
                .build();
        courierRequest = new CreateCourierRequest(List.of(createCourierDto_1, createCourierDto_2));

        CourierDto courierDto_1 = CourierDto.builder()
                .id(1L)
                .courierType(createCourierDto_1.getCourierType())
                .workingHours(createCourierDto_1.getWorkingHours())
                .regions(createCourierDto_1.getRegions())
                .build();
        CourierDto courierDto_2 = CourierDto.builder()
                .id(2L)
                .courierType(createCourierDto_2.getCourierType())
                .workingHours(createCourierDto_2.getWorkingHours())
                .regions(createCourierDto_2.getRegions())
                .build();
        couriersResponse = new CreateCouriersResponse(List.of(courierDto_1, courierDto_2));
    }

    @Test
    @DisplayName(value = "Метод createCourierRequest - valid 200")
    void saveCouriers_validInput_200() throws Exception {
        when(deliveryFacade.saveCouriers(any(CreateCourierRequest.class))).thenReturn(couriersResponse);

        mvc.perform(post("/couriers")
                        .content(objectMapper.writeValueAsString(courierRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(couriersResponse)));

        verify(deliveryFacade, times(1)).saveCouriers(any(CreateCourierRequest.class));
    }

    @Test
    @DisplayName(value = "Метод createCourierRequest - not valid 400")
    void saveCouriers_not_validInput_400() throws Exception {
        when(deliveryFacade.saveCouriers(any(CreateCourierRequest.class))).thenReturn(couriersResponse);

        MvcResult result = mvc.perform(post("/couriers")
                        .content(objectMapper.writeValueAsString(Collections.EMPTY_LIST))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        ProblemDetail problemDetail = objectMapper.readValue(result.getResponse().getContentAsString(), ProblemDetail.class);
        System.out.println(problemDetail.getStatus());
        System.out.println(problemDetail.getTitle());
        System.out.println(problemDetail.getDetail());
        System.out.println(problemDetail.getInstance());
        verify(deliveryFacade, times(0)).saveCouriers(any(CreateCourierRequest.class));
    }


}
