package com.gmail.mxwild.mealcalories.common;

import com.gmail.mxwild.mealcalories.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class Constants {

    private Constants() {
        throw new UnsupportedOperationException("This is util class can't be instance");
    }

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Breakfast", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Lunch", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Dinner", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Midnight eat", 100),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Breakfast", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Lunch", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Dinner", 410)
    );
}
