package com.gmail.mxwild.mealcalories;

import com.gmail.mxwild.mealcalories.model.Role;
import com.gmail.mxwild.mealcalories.model.User;

public class UserTestData {
    public static final Integer USER_ID = 1;
    public static final Integer ADMIN_ID = 2;
    public static final Integer NOT_FOUND = -100000;

    public static final User USER = new User(USER_ID, "User", "user@email.com", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@email.com", "password", Role.ADMIN);

}
