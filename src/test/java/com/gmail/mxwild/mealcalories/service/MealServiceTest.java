package com.gmail.mxwild.mealcalories.service;

import com.gmail.mxwild.mealcalories.model.Meal;
import com.gmail.mxwild.mealcalories.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.mxwild.mealcalories.MealTestData.ADMIN_MEAL1_ID;
import static com.gmail.mxwild.mealcalories.MealTestData.ADMIN_MEAL2;
import static com.gmail.mxwild.mealcalories.MealTestData.MEAL1;
import static com.gmail.mxwild.mealcalories.MealTestData.MEAL1_ID;
import static com.gmail.mxwild.mealcalories.MealTestData.NOT_FOUND_MEAL_ID;
import static com.gmail.mxwild.mealcalories.MealTestData.USER_MEALS;
import static com.gmail.mxwild.mealcalories.MealTestData.getNew;
import static com.gmail.mxwild.mealcalories.MealTestData.getUpdated;
import static com.gmail.mxwild.mealcalories.UserTestData.ADMIN_ID;
import static com.gmail.mxwild.mealcalories.UserTestData.NOT_FOUND_USER_ID;
import static com.gmail.mxwild.mealcalories.UserTestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertThat(newMeal)
                .usingRecursiveComparison()
                .isEqualTo(created);

        assertThat(service.get(newId, USER_ID))
                .usingRecursiveComparison()
                .isEqualTo(newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal duplicateMeal = new Meal(null, MEAL1.getDateTime(), "duplicate", 100);
        assertThrows(DataAccessException.class, () -> service.create(duplicateMeal, USER_ID));
    }

    @Test
    public void get() {
        Meal actualMeal = service.get(MEAL1_ID, USER_ID);
        assertThat(actualMeal)
                .usingRecursiveComparison()
                .isEqualTo(MEAL1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_MEAL_ID, NOT_FOUND_USER_ID));
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void getAll() {
        List<Meal> actualMeals = service.getAll(USER_ID);
        List<Meal> expectedMeals = USER_MEALS.stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());

        assertThat(actualMeals)
                .usingRecursiveComparison()
                .isEqualTo(expectedMeals);
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(
                LocalDate.of(2021, Month.JANUARY, 31), null, USER_ID);
        assertEquals(4, betweenInclusive.size());
    }

    @Test
    public void getBetweenWithNUllDates() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(null, null, USER_ID);
        assertEquals(7, betweenInclusive.size());
    }

    @Test

    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertThat(service.get(MEAL1_ID, USER_ID))
                .usingRecursiveComparison()
                .isEqualTo(updated);
    }

    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.update(MEAL1, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(ADMIN_MEAL1_ID, ADMIN_ID);
        assertThat(service.getAll(ADMIN_ID).get(0))
                .usingRecursiveComparison()
                .isEqualTo(ADMIN_MEAL2);
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(ADMIN_MEAL1_ID, USER_ID));
    }
}