package com.gmail.mxwild.mealcalories;

import com.gmail.mxwild.mealcalories.model.Role;
import com.gmail.mxwild.mealcalories.model.User;

import java.util.Collections;
import java.util.Date;

import static com.gmail.mxwild.mealcalories.common.Constants.START_SEQ;

public class UserTestData {

    public static final Integer USER_ID = START_SEQ;
    public static final Integer ADMIN_ID = START_SEQ + 1;
    public static final Integer NOT_FOUND_ID = -100000;

    public static final User USER = new User(USER_ID, "User", "user@email.com", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@email.com", "password", Role.ADMIN);

    public static final String[] EXCLUDED_FIELDS = {"registered", "roles"};

    public static User getUpdated() {
        User user = new User(USER);
        user.setEmail("update@mail.com");
        return user;
    }

    public static User getNew() {
        return new User(
                null,
                "newUser",
                "newUser@email.com",
                "newUserPass",
                true,
                new Date(),
                1500,
                Collections.singleton(Role.USER));
    }
}
