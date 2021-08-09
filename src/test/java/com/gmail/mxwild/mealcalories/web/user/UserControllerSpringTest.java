package com.gmail.mxwild.mealcalories.web.user;

import com.gmail.mxwild.mealcalories.repository.inmemory.InMemoryUserRepository;
import com.gmail.mxwild.mealcalories.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.gmail.mxwild.mealcalories.UserTestData.NOT_FOUND_ID;
import static com.gmail.mxwild.mealcalories.UserTestData.USER_ID;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-test.xml"})
public class UserControllerSpringTest {

    @Autowired
    private UserController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @Before
    public void setUp() {
        repository.init();
    }

    @Test
    public void delete() {
        controller.delete(USER_ID);
        assertNull(repository.get(USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND_ID));
    }
}