package com.gmail.mxwild.mealcalories.service;

import com.gmail.mxwild.mealcalories.model.Meal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.gmail.mxwild.mealcalories.MealTestData.MEAL1_ID;
import static com.gmail.mxwild.mealcalories.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        System.out.println(meal);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        System.out.println(meals);
    }

    @Test
    public void getBetweenInclusive() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}