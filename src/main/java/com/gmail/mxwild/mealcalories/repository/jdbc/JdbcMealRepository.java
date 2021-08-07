package com.gmail.mxwild.mealcalories.repository.jdbc;

import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.repository.MealRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {

    @Override
    public Meal save(Meal meal, Integer userId) {
        return null;
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        return false;
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        return null;
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return null;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return null;
    }
}
