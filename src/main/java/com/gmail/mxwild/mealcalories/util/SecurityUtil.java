package com.gmail.mxwild.mealcalories.util;

import static com.gmail.mxwild.mealcalories.common.Constants.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static int userId = 1;

    private SecurityUtil() {
        throw new UnsupportedOperationException("This is util class can't be instance");
    }

    public static int authUserId() {
        return userId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static void setAuthUserId(int userId) {
        SecurityUtil.userId = userId;
    }
}
