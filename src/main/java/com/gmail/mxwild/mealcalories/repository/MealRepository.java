package com.gmail.mxwild.mealcalories.repository;

import com.gmail.mxwild.mealcalories.model.Meal;

import java.util.Collection;

public interface MealRepository {

    Meal save(Meal meal);

    boolean delete(Integer id);

    Meal get(Integer id);

    Collection<Meal> getAll();

}
