package com.gmail.mxwild.mealcalories;

import com.gmail.mxwild.mealcalories.dto.MealDTO;
import com.gmail.mxwild.mealcalories.model.Role;
import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.web.meal.MealRestController;
import com.gmail.mxwild.mealcalories.web.user.UserController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext(
                "spring/spring-app.xml",
                "spring/spring-test.xml")) {
            System.out.println("Bean definition name: " + Arrays.asList(appCtx.getBeanDefinitionNames()));
            UserController userController = appCtx.getBean(UserController.class);
            userController.create(new User(null, "name", "email@mail.com", "password", Role.ADMIN));

            System.out.println();
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            List<MealDTO> filteredMealsBetween = mealRestController.getBetween(
                    LocalDate.of(2021, Month.JANUARY, 30), LocalTime.of(7, 0),
                    LocalDate.of(2021, Month.FEBRUARY, 1), LocalTime.of(11, 0)
            );
            filteredMealsBetween.forEach(System.out::println);
            System.out.println();
            System.out.println(mealRestController.getBetween(null, null, null, null));
        }
    }
}
