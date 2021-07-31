package com.gmail.mxwild.mealcalories;

import com.gmail.mxwild.mealcalories.model.Role;
import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.web.user.UserController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition name: " + Arrays.asList(appCtx.getBeanDefinitionNames()));
            UserController userController = appCtx.getBean(UserController.class);
            userController.create(new User(null, "name", "email@mail.com", "password", Role.ADMIN));
        }
    }
}
