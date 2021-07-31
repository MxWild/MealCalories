package com.gmail.mxwild.mealcalories.util;

import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.dto.MealDTO;

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

    public static List<MealDTO> getAll(Collection<Meal> meals, int caloriesPerDate) {
        return filtered(meals, caloriesPerDate);
    }

    private static List<MealDTO> filtered(Collection<Meal> meals, int caloriesPerDate) {
        Map<LocalDate, Integer> caloriesByDay = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return meals.stream()
                .map(meal -> createMealTo(meal, caloriesByDay.get(meal.getDate()) > caloriesPerDate))
                .collect(Collectors.toList());
    }

    private static MealDTO createMealTo(Meal meal, boolean excess) {
        return new MealDTO(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

}
