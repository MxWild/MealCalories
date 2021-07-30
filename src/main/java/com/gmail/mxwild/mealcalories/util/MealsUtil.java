package com.gmail.mxwild.mealcalories.util;

import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

    private MealsUtil() {
        throw new UnsupportedOperationException("This is util class can't be instance");
    }

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

    public static List<MealTo> getAll(Collection<Meal> meals, int caloriesPerDate) {
        return filtered(meals, caloriesPerDate);
    }

    private static List<MealTo> filtered(Collection<Meal> meals, int caloriesPerDate) {
        Map<LocalDate, Integer> caloriesByDay = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return meals.stream()
                .map(meal -> createMealTo(meal, caloriesByDay.get(meal.getDate()) > caloriesPerDate))
                .collect(Collectors.toList());
    }

    private static MealTo createMealTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

}
