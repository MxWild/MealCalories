package com.gmail.mxwild.mealcalories.repository.inmemory;

import com.gmail.mxwild.mealcalories.common.Constants;
import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.repository.MealRepository;
import com.gmail.mxwild.mealcalories.util.Util;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.gmail.mxwild.mealcalories.common.Constants.ADMIN_ID;
import static com.gmail.mxwild.mealcalories.common.Constants.USER_ID;
import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Logger log = getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        log.info("Start init example meals");
        Constants.MEALS.forEach(meal -> save(meal, USER_ID));

        save(new Meal(LocalDateTime.of(2021, Month.JULY, 31, 14, 0), "Admin lunch", 510), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2021, Month.JULY, 31, 21, 0), "Admin dinner", 1500), ADMIN_ID);

        log.info("End init example meals");
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        log.info("Save meal = {}", meal);
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(Integer mealId, Integer userId) {
        log.info("Delete meal with id = {}", mealId);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(mealId) != null;
    }

    @Override
    public Meal get(Integer mealId, Integer userId) {
        log.info("Get meal with mealId = {}", mealId);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals.get(mealId);
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

    private List<Meal> filteredByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = repository.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values()
                        .stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());

    }
}
