package ru.burtsev.yandexlavka2023;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

@UtilityClass
public class Constants {
    public static final DateTimeFormatter inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

    public static final DateTimeFormatter metaInfoFormatter =
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                    .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
                    .toFormatter().withLocale(Locale.ENGLISH);
}
