package com.gmail.mxwild.mealcalories.util;

import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

    public static void main(String[] args) {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Breakfast", 500),
                new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Lunch", 1000),
                new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Dinner", 500),
                new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Midnight eat", 100),
                new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Breakfast", 1000),
                new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Lunch", 500),
                new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Dinner", 410)
        );

        List<MealTo> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        List<MealTo> mealsToStreamFiltered = filteredByStream(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToStreamFiltered.forEach(System.out::println);
    }

    private static List<MealTo> filteredByStream(List<Meal> meals, LocalTime startTime,
                                                 LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesByDay = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
        System.out.println(caloriesByDay);

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createMealTo(meal, caloriesByDay.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static List<MealTo> filteredByCycles(List<Meal> meals, LocalTime startTime,
                                                 LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesByDay = new HashMap<>();
        meals.forEach(meal -> caloriesByDay.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        List<MealTo> mealWithExcesses = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                mealWithExcesses.add(createMealTo(meal, caloriesByDay.get(meal.getDate()) > caloriesPerDay
                ));
            }
        });

        return mealWithExcesses;
    }

    private static MealTo createMealTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
