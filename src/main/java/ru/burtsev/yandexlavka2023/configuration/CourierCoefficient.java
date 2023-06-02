package ru.burtsev.yandexlavka2023.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


@EnableConfigurationProperties
@Data
@Component
@ConfigurationProperties(prefix = "courier")
public class CourierCoefficient {

    Map<String, Integer> paymentCoefficient;
    Map<String, Integer> ratingCoefficient;
}
