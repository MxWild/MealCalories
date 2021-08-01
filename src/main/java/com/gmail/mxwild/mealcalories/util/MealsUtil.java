package com.gmail.mxwild.mealcalories.util;

import com.gmail.mxwild.mealcalories.dto.MealDTO;
import com.gmail.mxwild.mealcalories.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {

    private MealsUtil() {
        throw new UnsupportedOperationException("This is util class can't be instance");
    }

    public static List<MealDTO> getAll(Collection<Meal> meals, int caloriesPerDate) {
        return filtered(meals, caloriesPerDate, meal -> true);
    }

    public static List<MealDTO> getFilteredTo(List<Meal> meals, int authUserCaloriesPerDay,
                                              LocalTime startTime, LocalTime endTime) {
        return filtered(meals, authUserCaloriesPerDay, meal -> Util.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    private static List<MealDTO> filtered(Collection<Meal> meals, int caloriesPerDate, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesByDay = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return meals.stream()
                .filter(filter)
                .map(meal -> createMealTo(meal, caloriesByDay.get(meal.getDate()) > caloriesPerDate))
                .collect(Collectors.toList());
    }

    private static MealDTO createMealTo(Meal meal, boolean excess) {
        return new MealDTO(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
