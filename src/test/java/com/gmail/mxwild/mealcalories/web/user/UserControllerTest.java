package com.gmail.mxwild.mealcalories.web.user;

import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.repository.inmemory.InMemoryBaseRepository;
import com.gmail.mxwild.mealcalories.repository.inmemory.InMemoryUserRepository;
import com.gmail.mxwild.mealcalories.util.exception.NotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static com.gmail.mxwild.mealcalories.UserTestData.NOT_FOUND;
import static com.gmail.mxwild.mealcalories.UserTestData.USER_ID;
import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

public class UserControllerTest {

    private static final Logger log = getLogger(UserControllerTest.class);
    private static ConfigurableApplicationContext appCtx;
    private static UserController controller;
    private static InMemoryUserRepository repository;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        log.info("\n{}\n", Arrays.toString(appCtx.getBeanDefinitionNames()));
        controller = appCtx.getBean(UserController.class);
        repository = appCtx.getBean(InMemoryUserRepository.class);
    }

    @Before
    public void setUp() throws Exception {
        repository.init();
    }

    @After
    public void tearDown() throws Exception {
        appCtx.close();
    }

    @Test
    public void delete() {
        controller.delete(USER_ID);
        assertNull(repository.get(USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }
}