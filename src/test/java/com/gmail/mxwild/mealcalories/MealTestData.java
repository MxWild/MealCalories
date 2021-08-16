package com.gmail.mxwild.mealcalories;

import com.gmail.mxwild.mealcalories.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static com.gmail.mxwild.mealcalories.common.Constants.START_SEQ;

public class MealTestData {

    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL1_ID = START_SEQ + 9;
    public static final int NOT_FOUND_MEAL_ID = -10000;

    public static final String EXCLUDED_FIELDS = "user";

    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Breakfast", 500);
    public static final Meal MEAL2 = new Meal(MEAL1_ID + 1, LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Lunch", 1000);
    public static final Meal MEAL3 = new Meal(MEAL1_ID + 2, LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Dinner", 500);
    public static final Meal MEAL4 = new Meal(MEAL1_ID + 3, LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Midnight eat", 100);
    public static final Meal MEAL5 = new Meal(MEAL1_ID + 4, LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Breakfast", 1000);
    public static final Meal MEAL6 = new Meal(MEAL1_ID + 5, LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Lunch", 500);
    public static final Meal MEAL7 = new Meal(MEAL1_ID + 6, LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Dinner", 410);

    public static final List<Meal> USER_MEALS = Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6, MEAL7);

    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL1_ID, LocalDateTime.of(2021, Month.JULY, 31, 14, 0), "Admin lunch", 510);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL1_ID + 1, LocalDateTime.of(2021, Month.JULY, 31, 21, 0), "Admin dinner", 1500);

    public static final List<Meal> ADMIN_MEALS = Arrays.asList(ADMIN_MEAL1, ADMIN_MEAL2);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.JULY, 9, 18, 0), "New dinner", 300);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, MEAL1.getDateTime().plus(2, ChronoUnit.MINUTES), "Updated breakfast", 200);
    }

}
