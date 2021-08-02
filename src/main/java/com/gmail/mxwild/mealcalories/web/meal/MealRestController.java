package com.gmail.mxwild.mealcalories.web.meal;

import com.gmail.mxwild.mealcalories.dto.MealDTO;
import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.service.MealService;
import com.gmail.mxwild.mealcalories.util.MealsUtil;
import com.gmail.mxwild.mealcalories.util.SecurityUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.gmail.mxwild.mealcalories.util.ValidationUtil.assureIdConsistent;
import static com.gmail.mxwild.mealcalories.util.ValidationUtil.checkNew;
import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MealRestController {

    private static final Logger log = getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info(meal.isNew() ? "Create meal = {} for user = {}" : "Update meal = {} for user = {}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public Meal get(int mealId) {
        int userId = SecurityUtil.authUserId();
        log.info("Get meal = {} for user = {}", mealId, userId);
        return service.get(mealId, userId);
    }

    public List<MealDTO> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("Get all meals for user = {}", userId);
        return MealsUtil.getAll(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public void update(Meal meal, int mealId) {
        int userId = SecurityUtil.authUserId();
        log.info("Update meal = {} for user = {}", meal, userId);
        assureIdConsistent(meal, mealId);
        service.update(meal, userId);
    }

    public void delete(int mealId) {
        int userId = SecurityUtil.authUserId();
        log.info("Delete meal = {} for user = {}", mealId, userId);
        service.delete(mealId, userId);
    }

    public List<MealDTO> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                    @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("Get meals between dates = ({} - {}) times = ({} - {}) for user = {}",
                startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        return MealsUtil.getFilteredTo(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}
