package com.gmail.mxwild.mealcalories.repository.datajpa;

import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private static final Sort SORT_BY_DATETIME_DESC = Sort.by(Sort.Direction.DESC, "dateTime");

    private final CrudMealRepository crudMealRepository;
    private final CrudUserRepository crudUserRepository;

    @Autowired
    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Transactional
    @Override
    public Meal save(Meal meal, Integer userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(crudUserRepository.getById(userId));
        return crudMealRepository.save(meal);
    }

    @Transactional
    @Override
    public boolean delete(Integer id, Integer userId) {
        return crudMealRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        return crudMealRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return crudMealRepository.findAllByUserId(userId, SORT_BY_DATETIME_DESC);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository.getBetweenHalfOpen(startDateTime, endDateTime, userId);
    }
}
