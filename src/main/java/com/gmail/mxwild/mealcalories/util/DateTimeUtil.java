package com.gmail.mxwild.mealcalories.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.gmail.mxwild.mealcalories.common.Constants.MAX_DATE;
import static com.gmail.mxwild.mealcalories.common.Constants.MIN_DATE;

public class DateTimeUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeUtil() {
        throw new UnsupportedOperationException("This is util class can't be instance");
    }

    public static LocalDateTime asStartOfDayPrMin(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : MIN_DATE;
    }

    public static LocalDateTime asStartOfNextDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate.plus(1, ChronoUnit.DAYS).atStartOfDay() : MAX_DATE;
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? "" : localDateTime.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseLocalDate(String str) {
        return StringUtils.hasLength(str) ? LocalDate.parse(str) : null;
    }

    public static LocalTime parseLocalTime(String str) {
        return StringUtils.hasLength(str) ? LocalTime.parse(str) : null;
    }
}