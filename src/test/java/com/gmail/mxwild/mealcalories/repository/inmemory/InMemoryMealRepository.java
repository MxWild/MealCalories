package com.gmail.mxwild.mealcalories.repository.inmemory;

import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.repository.MealRepository;
import com.gmail.mxwild.mealcalories.util.Util;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.gmail.mxwild.mealcalories.MealTestData.ADMIN_MEALS;
import static com.gmail.mxwild.mealcalories.MealTestData.USER_MEALS;
import static com.gmail.mxwild.mealcalories.UserTestData.ADMIN_ID;
import static com.gmail.mxwild.mealcalories.UserTestData.USER_ID;
import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Logger log = getLogger(InMemoryMealRepository.class);

    private final Map<Integer, InMemoryBaseRepository<Meal>> repository = new ConcurrentHashMap<>();

    public InMemoryMealRepository() {
        log.info("Start init example meals");
        USER_MEALS.forEach(meal -> save(meal, USER_ID));
        ADMIN_MEALS.forEach(meal -> save(meal, ADMIN_ID));
        log.info("End init example meals");
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        Objects.requireNonNull(meal, "meal must not be null");
        InMemoryBaseRepository<Meal> meals = repository.computeIfAbsent(userId, uid -> new InMemoryBaseRepository<>());
        return meals.save(meal);
    }

    @Override
    public Meal get(Integer mealId, Integer userId) {
        InMemoryBaseRepository<Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(mealId);
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        log.info("Get all meals");
        return filteredByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return filteredByPredicate(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime));
    }

    @Override
    public boolean delete(Integer mealId, Integer userId) {
        InMemoryBaseRepository<Meal> meals = repository.get(userId);
        return meals != null && meals.delete(mealId);
    }

    private List<Meal> filteredByPredicate(int userId, Predicate<Meal> filter) {
        InMemoryBaseRepository<Meal> meals = repository.get(userId);
        return meals == null ? Collections.emptyList() :
                meals.getCollection()
                        .stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());

    }
}
