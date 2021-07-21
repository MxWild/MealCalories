package com.gmail.mxwild.mealcalories.util;

import java.time.LocalTime;

public class TimeUtil {

    private TimeUtil() {
        throw new UnsupportedOperationException("Util class don't be instance");
    }

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }
}