package com.gmail.mxwild.mealcalories.service;

import com.gmail.mxwild.mealcalories.model.User;
import com.gmail.mxwild.mealcalories.util.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.List;

import static com.gmail.mxwild.mealcalories.UserTestData.ADMIN;
import static com.gmail.mxwild.mealcalories.UserTestData.EXCLUDED_FIELDS;
import static com.gmail.mxwild.mealcalories.UserTestData.NOT_FOUND_USER_ID;
import static com.gmail.mxwild.mealcalories.UserTestData.USER;
import static com.gmail.mxwild.mealcalories.UserTestData.USER_ID;
import static com.gmail.mxwild.mealcalories.UserTestData.getNew;
import static com.gmail.mxwild.mealcalories.UserTestData.getUpdated;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    UserService service;

    @Autowired
    CacheManager cacheManager;

    @Before
    public void setup() {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() {
        User createdUser = service.create(getNew());
        Integer createdId = createdUser.getId();

        User newUser = getNew();
        newUser.setId(createdId);

        assertThat(service.get(createdId))
                .usingRecursiveComparison()
                .ignoringFields(EXCLUDED_FIELDS)
                .isEqualTo(newUser);

        service.delete(createdId);
    }

    @Test
    public void createWithDuplicateEmail() {
        User newUser = getNew();
        newUser.setEmail("user@email.com");
        assertThrows(DataAccessException.class, () -> service.create(newUser));
    }

    @Test
    public void get() {
        User actualUser = service.get(USER_ID);
        assertThat(actualUser)
                .usingRecursiveComparison()
                .ignoringFields(EXCLUDED_FIELDS)
                .isEqualTo(USER);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(-10000));
    }

    @Test
    public void getAll() {
        List<User> users = service.getAll();
        assertThat(users)
                .usingRecursiveComparison()
                .ignoringFields(EXCLUDED_FIELDS)
                .isEqualTo(Arrays.asList(ADMIN, USER));
    }

    @Test
    public void getByEmail() {
        User actual = service.getByEmail("user@email.com");
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields(EXCLUDED_FIELDS)
                .isEqualTo(USER);

    }

    @Test
    public void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByEmail("user@user.comm"));
    }

    @Test
    public void update() {
        User updatedUser = getUpdated();
        service.update(updatedUser);
        assertThat(updatedUser)
                .usingRecursiveComparison()
                .ignoringFields(EXCLUDED_FIELDS)
                .isEqualTo(service.get(USER_ID));
    }

    //TODO check update user not found
/*    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(getNew()));
    }*/

    @Test
    public void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_USER_ID));
    }
}