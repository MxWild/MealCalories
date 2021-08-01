package com.gmail.mxwild.mealcalories.repository;

import com.gmail.mxwild.mealcalories.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, Integer userId);

    boolean delete(Integer id, Integer userId);

    Meal get(Integer id, Integer userId);

    List<Meal> getAll(Integer userId);

    List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

}
