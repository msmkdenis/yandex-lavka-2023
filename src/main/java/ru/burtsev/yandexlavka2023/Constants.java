package ru.burtsev.yandexlavka2023;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class Constants {
    public static final DateTimeFormatter inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
}
