package ru.burtsev.yandexlavka2023.util;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class Constants {
    public static final DateTimeFormatter offsetFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

    public static final DateTimeFormatter localDateFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
