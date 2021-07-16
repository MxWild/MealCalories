package com.gmail.mxwild.mealcalories.util;

import com.gmail.mxwild.mealcalories.model.UserMeal;
import com.gmail.mxwild.mealcalories.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMealsUtil {

    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Breakfast", 500),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Lunch", 1000),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Dinner", 500),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Midnight eat", 100),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Breakfast", 1000),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Lunch", 500),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Dinner", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    private static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals,
                                                             LocalTime startTime,
                                                             LocalTime endTime,
                                                             int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesByDay = new HashMap<>();
        meals.forEach(meal -> caloriesByDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));
        System.out.println(caloriesByDay);

        List<UserMealWithExcess> mealWithExcesses = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealWithExcesses.add(new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesByDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay
                ));
            }
        });

        return mealWithExcesses;
    }
}
