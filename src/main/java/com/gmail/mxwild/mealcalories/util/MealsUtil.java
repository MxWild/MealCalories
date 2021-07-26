package com.gmail.mxwild.mealcalories.util;

import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

    public static final int CALORIES_PER_DAY = 2000;
    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Breakfast", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Lunch", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Dinner", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Midnight eat", 100),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Breakfast", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Lunch", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Dinner", 410)
    );

    public static List<MealTo> getAll(List<Meal> meals, int caloriesPerDate) {
        return filtered(meals, caloriesPerDate);
    }

    private static List<MealTo> filtered(List<Meal> meals, int caloriesPerDate) {
        Map<LocalDate, Integer> caloriesByDay = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return meals.stream()
                .map(meal -> createMealTo(meal, caloriesByDay.get(meal.getDate()) > caloriesPerDate))
                .collect(Collectors.toList());
    }

    private static MealTo createMealTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

/*    public static void main(String[] args) {

        List<MealTo> mealsTo = filteredByCycles(MEALS, LocalTime.of(7, 0), LocalTime.of(12, 0), CALORIES_PER_DAY);
        mealsTo.forEach(System.out::println);

        List<MealTo> mealsToStreamFiltered = filteredByStream(MEALS, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToStreamFiltered.forEach(System.out::println);
    }*/

/*    private static List<MealTo> filteredByStream(List<Meal> meals, LocalTime startTime,
                                                 LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesByDay = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createMealTo(meal, caloriesByDay.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }*/

/*    private static List<MealTo> filteredByCycles(List<Meal> meals, LocalTime startTime,
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
    }*/

}
